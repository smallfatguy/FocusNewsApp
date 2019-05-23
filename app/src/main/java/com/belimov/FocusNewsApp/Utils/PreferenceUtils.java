package com.belimov.FocusNewsApp.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceUtils {


    public static boolean contains(final Context context, final String name) {
        return getSharedPreferences(context).contains(name);
    }

    public static String loadString(final Context context, final String name) {
        return getSharedPreferences(context).getString(name, "");
    }

    public static int loadInteger(final Context context, final String name, final int defaultValue) {
        return getSharedPreferences(context).getInt(name, defaultValue);
    }

    public static void saveString(final Context context, final String name, final String value) {
        getSharedPreferences(context).edit().putString(name, value).apply();
    }

    private static SharedPreferences getSharedPreferences(final Context context) {
        return context.getSharedPreferences("SP", Application.MODE_PRIVATE);
    }

    public static void saveInteger(final Context context, final String name, final int value) {
        getSharedPreferences(context).edit().putInt(name, value).apply();
    }

    public static boolean loadBoolean(final Context context, final String name, final boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(name, defaultValue);
    }

    public static void saveBoolean(final Context context, final String name, final boolean value) {
        getSharedPreferences(context).edit().putBoolean(name, value).apply();
    }

    public static long loadLong(final Context context, final String name, final long defaultValue) {
        return getSharedPreferences(context).getLong(name, defaultValue);
    }

    public static void saveLong(final Context context, final String name, final long value) {
        getSharedPreferences(context).edit().putLong(name, value).apply();
    }
}
