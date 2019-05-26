package com.belimov.FocusNewsApp.features.updatescheduler.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class UpdateSchedulerDataSourceImpl implements UpdateSchedulerDataSource {

    private final static String AUTOUPDATE_SETTING_PREF = "autoupdate";

    private final static String AUTOUPDATE_DAYS_PREF = "autoupdate_days";
    private final static String AUTOUPDATE_HOURS_PREF = "autoupdate_hours";
    private final static String AUTOUPDATE_MINUTES_PREF = "autoupdate_minutes";

    private static final String SHARED_PREFERENCE_NAME = "SP";

    private final Context context;

    public UpdateSchedulerDataSourceImpl(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Application.MODE_PRIVATE);
    }


    @Override
    public void saveTime(int days, int hours, int minutes) {
        getSharedPreferences().edit().putInt(AUTOUPDATE_DAYS_PREF, days).apply();
        getSharedPreferences().edit().putInt(AUTOUPDATE_HOURS_PREF, hours).apply();
        getSharedPreferences().edit().putInt(AUTOUPDATE_MINUTES_PREF, minutes).apply();
    }

    @Override
    public Integer[] getTime() {
        return new Integer[]{getSharedPreferences().getInt(AUTOUPDATE_DAYS_PREF, 0),
                getSharedPreferences().getInt(AUTOUPDATE_HOURS_PREF, 0),
                getSharedPreferences().getInt(AUTOUPDATE_MINUTES_PREF, 15)};
    }

    @Override
    public void saveAutoupdateState(boolean state) {
        getSharedPreferences().edit().putBoolean(AUTOUPDATE_SETTING_PREF, state).apply();
    }

    @Override
    public boolean getAutoupdateState() {
        return getSharedPreferences().getBoolean(AUTOUPDATE_SETTING_PREF, false);
    }
}
