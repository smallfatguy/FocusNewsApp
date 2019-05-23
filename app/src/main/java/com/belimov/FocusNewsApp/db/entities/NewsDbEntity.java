package com.belimov.FocusNewsApp.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = ChannelDbEntity.class,
        parentColumns = "title", childColumns = "channelTitle", onDelete = CASCADE))
public class NewsDbEntity {

    @NonNull
    @PrimaryKey
    public String title;
    public String guid;
    public String description;
    public String newsLink;
    @ColumnInfo(index = true)
    public String channelTitle;

    @TypeConverters(DateConverter.class)
    public Date pubDate;

    public boolean isCollapsed;

    public NewsDbEntity(final String guid, @NonNull final String title, final String description, final String newsLink, final String channelTitle, final Date pubDate) {
        this.pubDate = pubDate;
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.newsLink = newsLink;
        this.channelTitle = channelTitle;
        this.isCollapsed = true;
    }
}
