package com.belimov.FocusNewsApp.features.news.domain;

import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public interface NewsInteractor {

    void loadNews(DataListener<List<News>> listener);

    void expandNews(String guid, Callback callback);

    void collapseNews(String guid, Callback callback);
}
