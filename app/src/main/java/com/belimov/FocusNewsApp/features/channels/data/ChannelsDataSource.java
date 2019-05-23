package com.belimov.FocusNewsApp.features.channels.data;

import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public interface ChannelsDataSource {

    void getChannels(DataListener<List<Channel>> listener);

    void createChannel(ChannelDto channel);

    void deleteChannel(String url);

    void changeChannelState(String url, boolean state);
}
