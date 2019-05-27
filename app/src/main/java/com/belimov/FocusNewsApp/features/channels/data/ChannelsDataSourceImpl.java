package com.belimov.FocusNewsApp.features.channels.data;

import com.belimov.FocusNewsApp.db.dao.ChannelsDao;
import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;
import com.belimov.FocusNewsApp.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class ChannelsDataSourceImpl implements ChannelsDataSource {

    private final ChannelsDao channelsDao;

    public ChannelsDataSourceImpl(final ChannelsDao channelsDao) {
        this.channelsDao = channelsDao;
    }

    @Override
    public void getChannels(final DataListener<List<Channel>> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Channel> channels = new ArrayList<>();
                for (final ChannelDbEntity channel : channelsDao.getChannels()) {
                    channels.add(new ChannelDto(channel).getChannelModel());
                }

                listener.onChanged(channels);
            }
        }).start();
    }

    @Override
    public void deleteChannel(final String url, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                channelsDao.delete(url);
                callback.onSuccess();
            }
        }).start();
    }

    @Override
    public void changeChannelState(final String url, final boolean state) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                channelsDao.changeChannelState(url, state);
            }
        }).start();
    }
}
