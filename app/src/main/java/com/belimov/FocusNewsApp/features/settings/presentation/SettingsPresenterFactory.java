package com.belimov.FocusNewsApp.features.settings.presentation;

import android.content.Context;

import com.belimov.FocusNewsApp.features.settings.data.SettingsDataSource;
import com.belimov.FocusNewsApp.features.settings.data.SettingsDataSourceImp;
import com.belimov.FocusNewsApp.features.settings.data.SettingsRepositoryImpl;

final class SettingsPresenterFactory {

    static SettingsPresenter createPresenter(final Context context) {
        final SettingsDataSource dataSource = new SettingsDataSourceImp(context);
        final SettingsRepository repository = new SettingsRepositoryImpl(dataSource);

        return new SettingsPresenter(repository);
    }
}
