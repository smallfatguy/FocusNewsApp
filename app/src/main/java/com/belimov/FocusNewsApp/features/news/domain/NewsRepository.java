package com.belimov.FocusNewsApp.features.news.domain;

import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public interface NewsRepository {

    void loadNews(DataListener<List<News>> listener);

    void createNews(News news);

    void changeNewsViewState(String guid, boolean state);
}
