package com.belimov.FocusNewsApp.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM NewsDbEntity ORDER BY pubDate DESC")
    List<NewsDbEntity> getNews();

    @Query("SELECT * FROM NewsDbEntity WHERE channelTitle IN (SELECT ChannelDbEntity.title FROM" +
            " ChannelDbEntity WHERE ChannelDbEntity.isActive) ORDER BY pubDate DESC")
    List<NewsDbEntity> getNewsFromActiveChannels();

    @Query("UPDATE NewsDbEntity SET isCollapsed = :state WHERE guid = :guid")
    void changeNewsViewState(String guid, boolean state);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NewsDbEntity news);
}
