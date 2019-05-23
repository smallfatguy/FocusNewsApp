package com.belimov.FocusNewsApp.features.channels.data;

import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;

public class ChannelDto {
    private String url;
    private String title;
    private String link;

    private boolean channelState;

    public ChannelDto(final Channel channel) {
        url = channel.getUrl();
        title = channel.getTitle();
        link = channel.getLink();
        channelState = channel.isActive();
    }

    public ChannelDto(final ChannelDbEntity channelDbEntity) {
        url = channelDbEntity.url;
        title = channelDbEntity.title;
        link = channelDbEntity.link;
        channelState = channelDbEntity.isActive;
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

    public Channel getChannelModel() {
        return new Channel(url, title, link, channelState);
    }

    public ChannelDbEntity getChannelDbModel() {
        return new ChannelDbEntity(url, title, link);
    }
}
