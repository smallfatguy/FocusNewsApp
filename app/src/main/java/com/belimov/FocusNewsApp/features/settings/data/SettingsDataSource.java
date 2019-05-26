package com.belimov.FocusNewsApp.features.settings.data;

import com.belimov.FocusNewsApp.utils.DataListener;

public interface SettingsDataSource {

    void saveChannelInput(String input);

    void getChannelInput(DataListener<String> listener);

    void saveSelectedTab(Integer page);

    void getSelectedTab(DataListener<Integer> listener);
}
