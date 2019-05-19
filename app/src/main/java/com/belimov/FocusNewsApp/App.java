package com.belimov.FocusNewsApp;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.belimov.FocusNewsApp.db.AppDB;
import com.facebook.stetho.Stetho;

public class App extends Application {

    private static Context appContext;
    private static AppDB appDB;

    public static Context getAppContext() {
        return appContext;
    }

    public static AppDB getDB() {
        return appDB;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        appDB = Room.databaseBuilder(this, AppDB.class, "DB").build();

        Stetho.initializeWithDefaults(this);
    }
}
