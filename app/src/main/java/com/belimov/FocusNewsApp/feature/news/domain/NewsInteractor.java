package com.belimov.FocusNewsApp.feature.news.domain;

public interface NewsInteractor {

    void loadNews();

    void refreshNewsFeed();

    void expandNews();

    void hideNews();

}
