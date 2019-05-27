package com.belimov.FocusNewsApp.features.news.data;

import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public interface NewsDataSource {

    void getNews(DataListener<List<News>> listener);

    void changeNewsViewState(String guid, boolean state, Callback callback);
}
