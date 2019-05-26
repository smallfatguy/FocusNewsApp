package com.belimov.FocusNewsApp.features.settings.presentation;

import com.belimov.FocusNewsApp.utils.DataListener;

public interface SettingsRepository {
    void saveChannelInput(String input);

    void loadChannelInput(DataListener<String> listener);

    void saveSelectedTab(Integer page);

    void loadSelectedTab(DataListener<Integer> listener);
}
