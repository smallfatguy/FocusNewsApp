package com.belimov.FocusNewsApp.features.news.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.news.domain.NewsInteractor;
import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.features.newsloader.LoaderWorker;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;
import com.belimov.FocusNewsApp.utils.WorkerUtils;

import java.util.List;

public class NewsPresenter extends MvpPresenter<NewsView> {

    public final MutableLiveData<List<News>> newsList = new MutableLiveData<>();
    private final NewsInteractor interactor;

    public NewsPresenter(NewsInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onViewReady() {
        attachDataObserver();
        loadNews();
    }

    void expandNews(final String guid) {
        interactor.expandNews(guid, new Callback() {
            @Override
            public void onSuccess() {
                loadNews();
            }

            @Override
            public void onFailure() {
            }
        });

    }

    void collapseNews(final String guid) {
        interactor.collapseNews(guid, new Callback() {
            @Override
            public void onSuccess() {
                loadNews();
            }

            @Override
            public void onFailure() {
            }
        });

    }

    public void refreshAllNews() {
        view.showRefreshing();
        final OneTimeWorkRequest refreshAllNewsWork = LoaderWorker.createOneTimeWork(new Data.Builder().build());

        WorkerUtils.runWork(view, refreshAllNewsWork, new Callback() {
            @Override
            public void onSuccess() {
                loadNews();
                view.hideRefreshing();
            }

            @Override
            public void onFailure() {
                view.hideRefreshing();
                view.showToast(view.getStringResource(R.string.load_channels_error));
            }
        });
    }

    private void loadNews() {
        interactor.loadNews(new DataListener<List<News>>() {
            @Override
            public void onChanged(List<News> data) {
                newsList.postValue(data);
            }
        });
    }

    private void attachDataObserver() {
        newsList.observe(view, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                if (news.size() != 0) {
                    view.hideMessage();
                } else {
                    view.showMessage();
                }
                view.showNews(news);
            }
        });
    }
}
