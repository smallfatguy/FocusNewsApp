package com.belimov.FocusNewsApp.feature.news.domain.model;

public class News {
    private String guid;
    private String title;
    private String description;
    private String newsLink;
    private String imageLink;

    public News(final String guid, final String title, final String description, final  String newsLink, final String imageLink) {
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.newsLink = newsLink;
        this.imageLink = imageLink;
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

    public String getImageLink() {
        return imageLink;
    }
}
