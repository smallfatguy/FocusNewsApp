package com.belimov.FocusNewsApp.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"guid", "title"}, foreignKeys = @ForeignKey(entity = ChannelDbEntity.class,
        parentColumns = "title", childColumns = "channelTitle", onDelete =  CASCADE))
public class NewsDbEntity {

    @NonNull
    public String guid;
    @NonNull
    public String title;
    public String description;
    public String newsLink;
    public String channelTitle;

    @TypeConverters(DateConverter.class)
    public Date pubDate;

    public NewsDbEntity(@NonNull final String guid, @NonNull final String title, final String description, final  String newsLink, final String channelTitle, final Date pubDate) {
        this.pubDate = pubDate;
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.newsLink = newsLink;
        this.channelTitle = channelTitle;
    }
}
