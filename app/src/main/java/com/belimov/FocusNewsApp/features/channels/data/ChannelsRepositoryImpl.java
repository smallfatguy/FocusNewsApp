package com.belimov.FocusNewsApp.features.channels.data;

import com.belimov.FocusNewsApp.features.channels.domain.ChannelsRepository;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public class ChannelsRepositoryImpl implements ChannelsRepository {

    private final ChannelsDataSource dataSource;

    public ChannelsRepositoryImpl(final ChannelsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadChannels(final DataListener<List<Channel>> listener) {
        dataSource.getChannels(listener);
    }

    @Override
    public void createChannel(final Channel channel) {
        dataSource.createChannel(new ChannelDto(channel));
    }

    @Override
    public void deleteChannel(final String url) {
        dataSource.deleteChannel(url);
    }

    @Override
    public void changeChannelState(final String url, final boolean state) {
        dataSource.changeChannelState(url, state);
    }
}
