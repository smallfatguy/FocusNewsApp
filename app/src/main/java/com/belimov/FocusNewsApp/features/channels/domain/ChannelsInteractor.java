package com.belimov.FocusNewsApp.features.channels.domain;

import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public interface ChannelsInteractor {
    void loadChannels(DataListener<List<Channel>> listener);

    void deleteChannel(String url);

    void createChannel(Channel channel);

    void changeChannelState(String url, boolean state);
}
