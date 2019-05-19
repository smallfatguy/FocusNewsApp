package com.belimov.FocusNewsApp.feature.news.data;

import android.util.Log;

import com.belimov.FocusNewsApp.App;
import com.belimov.FocusNewsApp.Utils.DatePicker;
import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static org.xmlpull.v1.XmlPullParserFactory.newInstance;

public class RssFeedParser {

    private static XmlPullParser parser;

    private static String channelUrl;
    private static String channelTitle;
    private static String channelLink;

    private static String guid;
    private static String title;
    private static String description;
    private static String newsLink ;
    private static Date pubDate;

    public static void parseFeed(final String url) {
        try {
            channelUrl = url;
            parser = initParser(getInputStream(new URL(channelUrl)));

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
        } catch (Throwable t) {
            Log.d("PARSER",t.toString(),t);
        }
    }

    private static void parseRSS() throws IOException, XmlPullParserException, ParseException {
        collectRssChannelInfo();
        while (parser.getEventType() != XmlPullParser.END_TAG || !Objects.equals(parser.getName(), "channel")) {
            if (!Objects.equals(parser.getText(), "\n")) {
                parseRssItems();
            }
            parser.next();
        }

    }

    private static void parseAtom() {

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
        App.getDB().channelsDao().insert(new ChannelDbEntity(channelUrl, channelTitle, channelLink));
    }

    private static void parseRssItems() throws IOException, XmlPullParserException {
        guid = "";
        title = "";
        description = "";
        newsLink = "";
        pubDate = new Date();
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
                            Log.d("PARSER",e.toString(),e);
                        }
                        break;
                }
            }
            parser.next();
        }
        App.getDB().newsDao().insert(new NewsDbEntity(guid, title, description, newsLink, channelTitle, pubDate));
    }

    private static XmlPullParser initParser(final InputStream inputStream) throws XmlPullParserException {
        final XmlPullParserFactory factory = newInstance();
        final XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, "UTF_8");

        return parser;
    }

    private static InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
