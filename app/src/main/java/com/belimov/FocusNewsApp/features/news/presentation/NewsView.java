package com.belimov.FocusNewsApp.features.news.presentation;

import com.belimov.FocusNewsApp.features.MvpView;
import com.belimov.FocusNewsApp.features.news.domain.model.News;

import java.util.List;

public interface NewsView extends MvpView {
    void showNews(List<News> news);

    void showMessage();

    void hideMessage();

    void showRefreshing();

    void hideRefreshing();

    void showToast(String message);

    String getStringResource(int id);
}
