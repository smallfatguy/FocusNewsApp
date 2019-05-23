package com.belimov.FocusNewsApp.features.news.domain;

import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public class NewsInteractorImpl implements NewsInteractor {

    private final NewsRepository repository;

    public NewsInteractorImpl(NewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void loadNews(DataListener<List<News>> listener) {
        repository.loadNews(listener);
    }

    @Override
    public void expandNews(String guid) {
        repository.changeNewsViewState(guid, false);
    }

    @Override
    public void collapseNews(String guid) {
        repository.changeNewsViewState(guid, true);
    }
}
