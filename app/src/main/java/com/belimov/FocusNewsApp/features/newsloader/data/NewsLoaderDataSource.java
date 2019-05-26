package com.belimov.FocusNewsApp.features.newsloader.data;

import com.belimov.FocusNewsApp.features.channels.data.ChannelDto;
import com.belimov.FocusNewsApp.features.news.data.NewsDto;

import java.io.IOException;
import java.io.InputStream;

public interface NewsLoaderDataSource {
    void createNews(NewsDto newsDto);

    void createChannel(ChannelDto channelDto);

    InputStream getInputStream(String url) throws IOException;

    String[] getChannelUrls();
}
