package com.belimov.FocusNewsApp.features.settings.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.belimov.FocusNewsApp.utils.DataListener;

public class SettingsDataSourceImp implements SettingsDataSource {

    private static final String SHARED_PREFERENCE_NAME = "SP";
    private static final String CHANNEL_INPUT_PREF = "channel input";
    private static final String LAST_OPENED_TAB_PREF = "last opened tab";

    private final Context context;

    public SettingsDataSourceImp(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Application.MODE_PRIVATE);
    }

    @Override
    public void saveChannelInput(final String input) {
        getSharedPreferences().edit().putString(CHANNEL_INPUT_PREF, input).apply();
    }

    @Override
    public void getChannelInput(final DataListener<String> listener) {
        final String channelInput = getSharedPreferences().getString(CHANNEL_INPUT_PREF, "");
        listener.onChanged(channelInput);
    }

    @Override
    public void saveSelectedTab(final Integer tab) {
        getSharedPreferences().edit().putInt(LAST_OPENED_TAB_PREF, tab).apply();
    }

    @Override
    public void getSelectedTab(final DataListener<Integer> listener) {
        final int lastTab = getSharedPreferences().getInt(LAST_OPENED_TAB_PREF, 0);
        listener.onChanged(lastTab);
    }
}
