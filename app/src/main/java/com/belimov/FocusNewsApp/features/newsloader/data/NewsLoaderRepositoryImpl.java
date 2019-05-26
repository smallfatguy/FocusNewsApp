package com.belimov.FocusNewsApp.features.newsloader.data;

import com.belimov.FocusNewsApp.features.channels.data.ChannelDto;
import com.belimov.FocusNewsApp.features.news.data.NewsDto;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class NewsLoaderRepositoryImpl implements NewsLoaderRepository {
    private final NewsLoaderDataSource dataSource;

    public NewsLoaderRepositoryImpl() {
        dataSource = new NewsLoaderDataSourceImpl();
    }

    @Override
    public void createNews(NewsDto newsDto) {
        dataSource.createNews(newsDto);
    }

    @Override
    public void createChannel(ChannelDto channelDto) {
        dataSource.createChannel(channelDto);
    }

    @Override
    public InputStream getInputStream(String url) throws IOException {
        return dataSource.getInputStream(url);
    }

    @Override
    public String[] getChannelUrls() {
        return dataSource.getChannelUrls();
    }

    @Override
    public void loadFeed(final String inputUrl) throws IOException, XmlPullParserException {
        final String[] urls;

        if (inputUrl != null) {
            urls = new String[]{inputUrl};
        } else {
            urls = getChannelUrls();
        }
        for (final String url : urls) {
            FeedParser.parseFeed(url, this);
        }
    }
}
