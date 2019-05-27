package com.belimov.FocusNewsApp.features.news.data;

import com.belimov.FocusNewsApp.db.dao.NewsDao;
import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;
import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.ArrayList;
import java.util.List;

public class NewsDataSourceImpl implements NewsDataSource {

    private final NewsDao newsDao;

    public NewsDataSourceImpl(final NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public void getNews(final DataListener<List<News>> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<News> news = new ArrayList<>();
                for (final NewsDbEntity newsFromDb : newsDao.getNewsFromActiveChannels()) {
                    news.add(new NewsDto(newsFromDb).getNewsModel());
                }

                listener.onChanged(news);
            }
        }).start();
    }

    @Override
    public void changeNewsViewState(final String guid, final boolean state, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsDao.changeNewsViewState(guid, state);
                callback.onSuccess();
            }
        }).start();
    }
}
