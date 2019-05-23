package com.belimov.FocusNewsApp.features.settings.loader.data;

import com.belimov.FocusNewsApp.features.channels.data.ChannelDto;
import com.belimov.FocusNewsApp.features.news.data.NewsDto;

import java.io.IOException;
import java.io.InputStream;

public interface NewsLoaderRepository {

    void createNews(NewsDto newsDto);

    void createChannel(ChannelDto channelDto);

    InputStream getInputStream(String url) throws IOException;

    String[] getChannelUrls();
}
