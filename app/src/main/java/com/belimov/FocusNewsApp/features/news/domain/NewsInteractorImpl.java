package com.belimov.FocusNewsApp.features.news.domain;

import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public class NewsInteractorImpl implements NewsInteractor {

    private final NewsRepository repository;

    public NewsInteractorImpl(final NewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void loadNews(final DataListener<List<News>> listener) {
        repository.loadNews(listener);
    }

    @Override
    public void expandNews(final String guid, final Callback callback) {
        repository.changeNewsViewState(guid, false, callback);
    }

    @Override
    public void collapseNews(final String guid, final Callback callback) {
        repository.changeNewsViewState(guid, true, callback);
    }
}
