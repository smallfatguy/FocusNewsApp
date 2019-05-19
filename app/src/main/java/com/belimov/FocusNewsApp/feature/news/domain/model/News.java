package com.belimov.FocusNewsApp.feature.news.domain.model;

import java.util.Date;

public class News {
    private String guid;
    private String title;
    private String description;
    private String newsLink;
    private String channelTitle;
    private Date pubDate;

    public News(final String guid, final String title, final String description, final  String newsLink, final String channelTitle, final Date pubDate) {
        this.pubDate = pubDate;
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.newsLink = newsLink;
        this.channelTitle = channelTitle;
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
}
