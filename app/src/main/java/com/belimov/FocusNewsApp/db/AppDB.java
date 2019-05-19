package com.belimov.FocusNewsApp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.belimov.FocusNewsApp.db.dao.ChannelsDao;
import com.belimov.FocusNewsApp.db.dao.NewsDao;
import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;

@Database(entities = {NewsDbEntity.class, ChannelDbEntity.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract NewsDao newsDao();
    public abstract ChannelsDao channelsDao();
}
