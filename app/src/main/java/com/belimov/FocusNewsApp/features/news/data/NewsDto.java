package com.belimov.FocusNewsApp.features.news.data;

import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;
import com.belimov.FocusNewsApp.features.news.domain.model.News;

import java.util.Date;

public class NewsDto {
    private String guid;
    private String title;
    private String description;
    private String newsLink;
    private String channelTitle;
    private Boolean isCollapsed;
    private Date pubDate;

    public NewsDto(final News news) {
        this.pubDate = news.getPubDate();
        this.guid = news.getGuid();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.newsLink = news.getNewsLink();
        this.channelTitle = news.getChannelTitle();
        this.isCollapsed = news.isCollapsed();
    }

    public NewsDto(final NewsDbEntity news) {
        this.pubDate = news.pubDate;
        this.guid = news.guid;
        this.title = news.title;
        this.description = news.description;
        this.newsLink = news.newsLink;
        this.channelTitle = news.channelTitle;
        this.isCollapsed = news.isCollapsed;
    }

    public String getGuid() {
        return guid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public News getNewsModel() {
        return new News(guid, title, description, newsLink, channelTitle, pubDate, isCollapsed);
    }

    public NewsDbEntity getNewsDbModel() {
        return new NewsDbEntity(guid, title, description, newsLink, channelTitle, pubDate);
    }
}
