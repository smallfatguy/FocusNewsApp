package com.belimov.FocusNewsApp.features.news.presentation;

import com.belimov.FocusNewsApp.App;
import com.belimov.FocusNewsApp.db.dao.NewsDao;
import com.belimov.FocusNewsApp.features.news.data.NewsDataSource;
import com.belimov.FocusNewsApp.features.news.data.NewsDataSourceImpl;
import com.belimov.FocusNewsApp.features.news.data.NewsRepositoryImpl;
import com.belimov.FocusNewsApp.features.news.domain.NewsInteractor;
import com.belimov.FocusNewsApp.features.news.domain.NewsInteractorImpl;
import com.belimov.FocusNewsApp.features.news.domain.NewsRepository;

final class NewsPresenterFactory {

    static NewsPresenter createPresenter() {
        final NewsDao newsDao = App.getDB().newsDao();

        final NewsDataSource dataSource = new NewsDataSourceImpl(newsDao);
        final NewsRepository repository = new NewsRepositoryImpl(dataSource);
        final NewsInteractor interactor = new NewsInteractorImpl(repository);

        return new NewsPresenter(interactor);
    }
}
