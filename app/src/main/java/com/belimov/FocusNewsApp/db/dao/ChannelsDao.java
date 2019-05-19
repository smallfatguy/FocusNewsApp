package com.belimov.FocusNewsApp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;

import java.util.List;

@Dao
public interface ChannelsDao {

    @Query("SELECT * FROM ChannelDbEntity")
   List<ChannelDbEntity> getChannels();

    @Query("DELETE FROM ChannelDbEntity where url = :url")
    void delete(String url);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ChannelDbEntity news);
}
