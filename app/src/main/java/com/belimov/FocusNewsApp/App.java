package com.belimov.FocusNewsApp;

import android.app.Application;

import androidx.room.Room;

import com.belimov.FocusNewsApp.db.AppDB;

public class App extends Application {

    private static AppDB appDB;

    public static AppDB getDB() {
        return appDB;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appDB = Room.databaseBuilder(this, AppDB.class, "DB").build();
    }
}
