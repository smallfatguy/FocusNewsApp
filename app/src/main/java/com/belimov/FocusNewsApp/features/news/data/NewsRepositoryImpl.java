package com.belimov.FocusNewsApp.features.news.data;

import com.belimov.FocusNewsApp.features.news.domain.NewsRepository;
import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public class NewsRepositoryImpl implements NewsRepository {
    private final NewsDataSource dataSource;

    public NewsRepositoryImpl(NewsDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void loadNews(final DataListener<List<News>> listener) {
        dataSource.getNews(listener);
    }

    @Override
    public void changeNewsViewState(final String guid, final boolean state, final Callback callback) {
        dataSource.changeNewsViewState(guid, state, callback);
    }
}
