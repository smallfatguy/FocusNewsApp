package com.belimov.FocusNewsApp.features.settings.data;

import com.belimov.FocusNewsApp.features.settings.presentation.SettingsRepository;
import com.belimov.FocusNewsApp.utils.DataListener;

public class SettingsRepositoryImpl implements SettingsRepository {
    private final SettingsDataSource dataSource;

    public SettingsRepositoryImpl(SettingsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveChannelInput(final String input) {
        dataSource.saveChannelInput(input);
    }

    @Override
    public void loadChannelInput(final DataListener<String> listener) {
        dataSource.getChannelInput(listener);
    }

    @Override
    public void saveSelectedTab(final Integer tab) {
        dataSource.saveSelectedTab(tab);
    }

    @Override
    public void loadSelectedTab(final DataListener<Integer> listener) {
        dataSource.getSelectedTab(listener);
    }
}
