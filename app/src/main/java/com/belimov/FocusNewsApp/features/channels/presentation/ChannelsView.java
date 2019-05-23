package com.belimov.FocusNewsApp.features.channels.presentation;

import com.belimov.FocusNewsApp.features.MvpView;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;

import java.util.List;

interface ChannelsView extends MvpView {

    void showChannelsList(List<Channel> channels);
}
