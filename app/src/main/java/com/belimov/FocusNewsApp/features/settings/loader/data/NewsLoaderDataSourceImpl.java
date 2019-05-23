package com.belimov.FocusNewsApp.features.settings.loader.data;

import com.belimov.FocusNewsApp.App;
import com.belimov.FocusNewsApp.db.dao.ChannelsDao;
import com.belimov.FocusNewsApp.db.dao.NewsDao;
import com.belimov.FocusNewsApp.features.channels.data.ChannelDto;
import com.belimov.FocusNewsApp.features.news.data.NewsDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NewsLoaderDataSourceImpl implements NewsLoaderDataSource {
    private final ChannelsDao channelsDao;
    private final NewsDao newsDao;

    public NewsLoaderDataSourceImpl() {
        channelsDao = App.getDB().channelsDao();
        newsDao = App.getDB().newsDao();
    }

    @Override
    public void createNews(final NewsDto newsDto) {
        newsDao.insert(newsDto.getNewsDbModel());
    }

    @Override
    public void createChannel(final ChannelDto channelDto) {
        channelsDao.insert(channelDto.getChannelDbModel());
    }

    @Override
    public InputStream getInputStream(String url) throws IOException {
        return new URL(url).openConnection().getInputStream();
    }

    @Override
    public String[] getChannelUrls() {
        return channelsDao.getChannelUrls();
    }
}
