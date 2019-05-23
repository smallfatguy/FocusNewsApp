package com.belimov.FocusNewsApp.features.settings.loader;

import android.util.Log;

import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;
import com.belimov.FocusNewsApp.features.channels.data.ChannelDto;
import com.belimov.FocusNewsApp.features.news.data.NewsDto;
import com.belimov.FocusNewsApp.features.settings.loader.data.NewsLoaderRepository;
import com.belimov.FocusNewsApp.utils.DatePicker;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static org.xmlpull.v1.XmlPullParserFactory.newInstance;

class FeedParser {

    private static NewsLoaderRepository repository;

    private static XmlPullParser parser;

    private static String channelUrl;
    private static String channelTitle;
    private static String channelLink;

    private static String guid;
    private static String title;
    private static String description;
    private static String newsLink;
    private static Date pubDate;

    static void parseFeed(final String url, final NewsLoaderRepository loaderRepository) throws IOException, XmlPullParserException {
        repository = loaderRepository;
        channelUrl = url;
        parser = initParser(repository.getInputStream(channelUrl));

        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                switch (parser.getName()) {
                    case "channel":
                        parseRSS();
                        break;
                    case "feed":
                        parseAtom();
                        break;
                }
            }
            parser.next();
        }
    }

    private static void parseRSS() throws IOException, XmlPullParserException {
        collectRssChannelInfo();
        while (parser.getEventType() != XmlPullParser.END_TAG || !Objects.equals(parser.getName(), "channel")) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                parseRssItems();
            }
            parser.next();
        }

    }

    private static void parseAtom() throws IOException, XmlPullParserException {
        collectAtomChannelInfo();
        while (parser.getEventType() != XmlPullParser.END_TAG || !Objects.equals(parser.getName(), "feed")) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                parseAtomEntries();
            }
            parser.next();
        }
    }

    private static void collectAtomChannelInfo() throws XmlPullParserException, IOException {
        channelLink = channelUrl;
        channelTitle = channelUrl;

        while (!Objects.equals(parser.getName(), "entry")) {
            if (parser.getEventType() == XmlPullParser.START_TAG && parser.getDepth() == 2) {
                switch (parser.getName()) {
                    case "title":
                        channelTitle = parser.nextText();
                        break;
                    case "link":
                        channelLink = parser.getAttributeValue(null, "href");
                        break;
                }
            }
            parser.next();
        }
        repository.createChannel(new ChannelDto(new ChannelDbEntity(channelUrl, channelTitle, channelLink)));
    }

    private static void collectRssChannelInfo() throws IOException, XmlPullParserException {
        channelTitle = channelUrl;
        channelLink = channelUrl;

        while (!Objects.equals(parser.getName(), "item")) {
            if (parser.getEventType() == XmlPullParser.START_TAG && parser.getDepth() == 3) {
                switch (parser.getName()) {
                    case "title":
                        channelTitle = parser.nextText();
                        break;
                    case "link":
                        channelLink = parser.nextText();
                        break;
                }
            }
            parser.next();
        }
        repository.createChannel(new ChannelDto(new ChannelDbEntity(channelUrl, channelTitle, channelLink)));
    }

    private static void parseAtomEntries() throws IOException, XmlPullParserException {
        clearNewsFields();
        while (parser.getEventType() != XmlPullParser.END_TAG || !Objects.equals(parser.getName(), "entry")) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                switch (parser.getName()) {
                    case "id":
                        guid = parser.nextText();
                        break;
                    case "title":
                        title = parser.nextText();
                        break;
                    case "link":
                        newsLink = parser.getAttributeValue(null, "href");
                        break;
                    case "summary":
                        description = parser.nextText();
                        break;
                    case "published":
                        try {
                            String date = parser.nextText();
                            pubDate = new SimpleDateFormat(DatePicker.determineDateFormat(date), Locale.ENGLISH).parse(date);
                        } catch (Exception e) {
                            Log.d("PARSER", e.toString(), e);
                        }
                        break;
                }
            }
            parser.next();
        }
        repository.createNews(new NewsDto(new NewsDbEntity(guid, title, description, newsLink, channelTitle, pubDate)));
    }

    private static void parseRssItems() throws IOException, XmlPullParserException {
        clearNewsFields();
        while (parser.getEventType() != XmlPullParser.END_TAG || !Objects.equals(parser.getName(), "item")) {
            if (parser.getEventType() == XmlPullParser.START_TAG) {
                switch (parser.getName()) {
                    case "guid":
                        guid = parser.nextText();
                        break;
                    case "title":
                        title = parser.nextText();
                        break;
                    case "link":
                        newsLink = parser.nextText();
                        break;
                    case "description":
                        description = parser.nextText();
                        break;
                    case "pubDate":
                        try {
                            String date = parser.nextText();
                            pubDate = new SimpleDateFormat(DatePicker.determineDateFormat(date), Locale.ENGLISH).parse(date);
                        } catch (ParseException e) {
                            Log.d("PARSER", e.toString(), e);
                        }
                        break;
                }
            }
            parser.next();
        }
        repository.createNews(new NewsDto(new NewsDbEntity(guid, title, description, newsLink, channelTitle, pubDate)));
    }

    private static XmlPullParser initParser(final InputStream inputStream) throws XmlPullParserException {
        final XmlPullParserFactory factory = newInstance();
        final XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, "UTF_8");

        return parser;
    }

    private static void clearNewsFields() {
        guid = "";
        title = "";
        description = "";
        newsLink = "";
        pubDate = new Date();
    }
}
