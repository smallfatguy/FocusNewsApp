package com.belimov.FocusNewsApp.features.settings.presentation;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.newsloader.LoaderWorker;
import com.belimov.FocusNewsApp.utils.Callback;
import com.belimov.FocusNewsApp.utils.DataListener;
import com.belimov.FocusNewsApp.utils.WorkerUtils;

public class SettingsPresenter extends MvpPresenter<SettingsView> {

    private final SettingsRepository repository;

    public SettingsPresenter(final SettingsRepository repository) {
        this.repository = repository;
    }

    void saveChannelInput(final String input) {
        repository.saveChannelInput(input);
    }

    void loadChannelInput() {
        repository.loadChannelInput(new DataListener<String>() {
            @Override
            public void onChanged(String data) {
                view.setChannelInput(data);
            }
        });
    }

    void setCurrentTab() {
        repository.loadSelectedTab(new DataListener<Integer>() {
            @Override
            public void onChanged(Integer data) {
                view.setCurrentTab(data);
            }
        });
    }

    void saveCurrentTab(final int tab) {
        repository.saveSelectedTab(tab);
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        loadChannelInput();
        setCurrentTab();
    }

    void uploadChannel() {
        String url = view.getTextFromField();

        if (!url.isEmpty()) {
            view.hideKeyboard();
            view.showLoading();

            final Data workerInput = new Data.Builder().putString(LoaderWorker.INPUT_URL_KEY, url).build();
            final OneTimeWorkRequest loadChannelsWork = LoaderWorker.createOneTimeWork(workerInput);

            WorkerUtils.runWork(view, loadChannelsWork, new Callback() {
                @Override
                public void onSuccess() {
                    view.refreshViewPager();
                    view.clearChannelInput();
                    view.hideLoading();
                }

                @Override
                public void onFailure() {
                    view.hideLoading();
                    view.showToast(view.getStringResource(R.string.load_channels_error));
                }
            });
        } else {
            view.showToast(view.getStringResource(R.string.empty_channel_input_error));
        }
    }
}
