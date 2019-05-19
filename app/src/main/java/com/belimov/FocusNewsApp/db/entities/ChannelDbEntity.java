package com.belimov.FocusNewsApp.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(primaryKeys = {"url", "title"}, indices = {@Index(value = "title", unique = true)})
public class ChannelDbEntity {

    @NonNull
    public String url;

    @NonNull
    @ColumnInfo(name = "title")
    public String title;

    public String link;

    public boolean isActive;

    public ChannelDbEntity(@NonNull final String url, @NonNull final String title, final String link) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.isActive = true;
    }


}
