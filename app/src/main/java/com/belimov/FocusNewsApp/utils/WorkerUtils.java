package com.belimov.FocusNewsApp.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.belimov.FocusNewsApp.features.newsloader.LoaderWorker;

public class WorkerUtils {

    public static void runWork(final LifecycleOwner owner, final OneTimeWorkRequest workRequest, final Callback callback) {
        WorkManager.getInstance().enqueue(workRequest);

        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.getId())
                .observe(owner, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            callback.onSuccess();
                        } else if (workInfo == null || workInfo.getState() == WorkInfo.State.FAILED) {
                            callback.onFailure();
                        }
                    }
                });
    }

    public static void enqueueAutoupdateWorker(final PeriodicWorkRequest workRequest) {
        WorkManager.getInstance().enqueueUniquePeriodicWork(LoaderWorker.AUTOUPDATE_TAG, ExistingPeriodicWorkPolicy.REPLACE, workRequest);
    }

    public static void cancelAutoupdateWorker() {
        WorkManager.getInstance().cancelAllWorkByTag(LoaderWorker.AUTOUPDATE_TAG);
    }
}
