package com.belimov.FocusNewsApp.feature.settings.data.model;

public class Channel {
    private String url;
    private String title;

    private String link;

    private boolean channelState;

    public Channel(final String url, final String title, final String link, final boolean channelState) {
        this.url = url;
        this.title = title;
        this.link = link;
        this.channelState = channelState;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public boolean isActive() {
        return channelState;
    }
}
