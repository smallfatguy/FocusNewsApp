package com.belimov.FocusNewsApp.features.channels.domain;

import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public class ChannelsInteractorImpl implements ChannelsInteractor {

    private final ChannelsRepository repository;

    public ChannelsInteractorImpl(final ChannelsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void loadChannels(final DataListener<List<Channel>> listener) {
        repository.loadChannels(listener);
    }

    @Override
    public void deleteChannel(final String url) {
        repository.deleteChannel(url);
    }

    @Override
    public void createChannel(final Channel channel) {
        repository.createChannel(channel);
    }

    @Override
    public void changeChannelState(final String url, final boolean state) {
        repository.changeChannelState(url, state);
    }
}
