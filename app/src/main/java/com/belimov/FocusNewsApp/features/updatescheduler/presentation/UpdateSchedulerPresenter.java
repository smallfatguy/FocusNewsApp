package com.belimov.FocusNewsApp.features.updatescheduler.presentation;

import androidx.work.PeriodicWorkRequest;

import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.newsloader.LoaderWorker;
import com.belimov.FocusNewsApp.utils.WorkerUtils;

public class UpdateSchedulerPresenter extends MvpPresenter<UpdateSchedulerView> {

    private final UpdateSchedulerRepository repository ;

    public UpdateSchedulerPresenter(UpdateSchedulerRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        setControls();
    }

    void setControls() {
        final boolean autoupdateState = repository.loadAutoupdateState();
        final Integer[] time = repository.loadTime();

        view.setControls(autoupdateState, time[0], time[1], time[2]);
    }

    void saveTime(final int days, final int hours, final int minutes) {
        repository.saveTime(days, hours, minutes);
    }

    void enqueueAutoupdateWorker() {
        final PeriodicWorkRequest request = LoaderWorker.createPeriodicWork(repository.calculateTime());
        WorkerUtils.enqueueAutoupdateWorker(request);
    }

    void cancelAutoupdateWorker() {
        WorkerUtils.cancelAutoupdateWorker();
    }

    void saveAutoupdateState(final boolean state) {
        repository.saveAutoupdateState(state);
    }
}
