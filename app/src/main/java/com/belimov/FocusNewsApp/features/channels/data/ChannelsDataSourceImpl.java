package com.belimov.FocusNewsApp.features.channels.data;

import com.belimov.FocusNewsApp.db.dao.ChannelsDao;
import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.ArrayList;
import java.util.List;

public class ChannelsDataSourceImpl implements ChannelsDataSource {

    private final ChannelsDao channelsDao;

    public ChannelsDataSourceImpl(final ChannelsDao channelsDao) {
        this.channelsDao = channelsDao;
    }

    @Override
    public void getChannels(final DataListener<List<Channel>> listener) {
        final List<Channel> channels = new ArrayList<>();
        for (final ChannelDbEntity channel : channelsDao.getChannels()) {
            channels.add(new ChannelDto(channel).getChannelModel());
        }

        listener.onChanged(channels);
    }

    @Override
    public void createChannel(final ChannelDto channel) {
        channelsDao.insert(channel.getChannelDbModel());
    }

    @Override
    public void deleteChannel(final String url) {
        channelsDao.delete(url);
    }

    @Override
    public void changeChannelState(final String url, final boolean state) {
        channelsDao.changeChannelState(url, state);
    }
}
