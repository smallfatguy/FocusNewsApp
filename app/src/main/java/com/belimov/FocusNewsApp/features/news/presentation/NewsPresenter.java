package com.belimov.FocusNewsApp.features.news.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.news.domain.NewsInteractor;
import com.belimov.FocusNewsApp.features.news.domain.model.News;
import com.belimov.FocusNewsApp.features.settings.loader.LoaderWorker;
import com.belimov.FocusNewsApp.utils.DataListener;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.expandNews(guid);
                loadNews();
            }
        }).start();
    }

    void collapseNews(final String guid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.collapseNews(guid);
                loadNews();
            }
        }).start();
    }

    public void refreshAllNews() {
        view.showRefreshing();
        final OneTimeWorkRequest refreshAllNewsWork = LoaderWorker.createOneTimeWork(new Data.Builder().build());
        WorkManager.getInstance().enqueue(refreshAllNewsWork);

        WorkManager.getInstance().getWorkInfoByIdLiveData(refreshAllNewsWork.getId())
                .observe(view, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            loadNews();
                            view.hideRefreshing();
                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            view.hideRefreshing();
                            view.showToast(view.getStringResource(R.string.load_channels_error));
                        }
                    }
                });
    }

    private void loadNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.loadNews(new DataListener<List<News>>() {
                    @Override
                    public void onChanged(List<News> data) {
                        newsList.postValue(data);
                    }
                });
            }
        }).start();
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
