package com.belimov.FocusNewsApp.features.settings.presentation;

import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.settings.loader.LoaderWorker;

public class SettingsPresenter extends MvpPresenter<SettingsView> {

    void uploadChannel() {
        String[] urls = new String[]{view.getTextFromField()};

        if (!urls[0].isEmpty()) {
            view.hideKeyboard();
            view.showLoading();

            final Data workerInput = new Data.Builder().putStringArray(LoaderWorker.INPUT_URLS_KEY, urls).build();
            final OneTimeWorkRequest loadChannelsWork = LoaderWorker.createOneTimeWork(workerInput);

            WorkManager.getInstance().enqueue(loadChannelsWork);

            WorkManager.getInstance().getWorkInfoByIdLiveData(loadChannelsWork.getId())
                    .observe(view, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                view.refreshViewPager();
                                view.clearChannelInput();
                                view.hideLoading();
                            } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                                view.hideLoading();
                                view.showToast(view.getStringResource(R.string.load_channels_error));
                            }
                        }
                    });
        } else {
            view.showToast(view.getStringResource(R.string.empty_channel_input_error));
        }
    }
}
