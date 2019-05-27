package com.belimov.FocusNewsApp.features.channels.data;

import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;
import com.belimov.FocusNewsApp.utils.Callback;

import java.util.List;

public interface ChannelsDataSource {

    void getChannels(DataListener<List<Channel>> listener);

    void deleteChannel(String url, Callback callback);

    void changeChannelState(String url, boolean state);
}
