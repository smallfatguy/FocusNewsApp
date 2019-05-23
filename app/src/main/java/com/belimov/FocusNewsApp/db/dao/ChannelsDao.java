package com.belimov.FocusNewsApp.db.dao;

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

    @Query("SELECT url FROM ChannelDbEntity WHERE isActive")
    String[] getChannelUrls();

    @Query("DELETE FROM ChannelDbEntity WHERE url = :url")
    void delete(String url);

    @Query("UPDATE ChannelDbEntity SET isActive = :state WHERE url = :url")
    void changeChannelState(String url, boolean state);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ChannelDbEntity channel);
}
