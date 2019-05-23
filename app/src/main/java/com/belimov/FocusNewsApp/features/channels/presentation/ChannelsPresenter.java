package com.belimov.FocusNewsApp.features.channels.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.channels.domain.ChannelsInteractor;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;
import com.belimov.FocusNewsApp.utils.DataListener;

import java.util.List;

public class ChannelsPresenter extends MvpPresenter<ChannelsView> {

    private final MutableLiveData<List<Channel>> channelsList = new MutableLiveData<>();
    private final ChannelsInteractor interactor;

    ChannelsPresenter(final ChannelsInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    protected void onViewReady() {
        attachDataObserver();
        loadChannels();
    }

    void onChangeState(final String url, final boolean state) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.changeChannelState(url, state);
            }
        }).start();
    }

    private void loadChannels() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.loadChannels(new DataListener<List<Channel>>() {
                    @Override
                    public void onChanged(List<Channel> data) {
                        channelsList.postValue(data);
                    }
                });
            }
        }).start();
    }

    void deleteChannel(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.deleteChannel(url);
                loadChannels();
            }
        }).start();
    }

    private void attachDataObserver() {
        channelsList.observe(view, new Observer<List<Channel>>() {
            @Override
            public void onChanged(List<Channel> channels) {
                view.showChannelsList(channels);
            }
        });
    }
}
