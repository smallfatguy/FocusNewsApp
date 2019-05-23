package com.belimov.FocusNewsApp.features.channels.presentation;

import com.belimov.FocusNewsApp.App;
import com.belimov.FocusNewsApp.db.dao.ChannelsDao;
import com.belimov.FocusNewsApp.features.channels.data.ChannelsDataSource;
import com.belimov.FocusNewsApp.features.channels.data.ChannelsDataSourceImpl;
import com.belimov.FocusNewsApp.features.channels.data.ChannelsRepositoryImpl;
import com.belimov.FocusNewsApp.features.channels.domain.ChannelsInteractor;
import com.belimov.FocusNewsApp.features.channels.domain.ChannelsInteractorImpl;
import com.belimov.FocusNewsApp.features.channels.domain.ChannelsRepository;

final class ChannelsPresenterFactory {

    static ChannelsPresenter createPresenter() {
        final ChannelsDao channelsDao = App.getDB().channelsDao();

        final ChannelsDataSource dataSource = new ChannelsDataSourceImpl(channelsDao);
        final ChannelsRepository repository = new ChannelsRepositoryImpl(dataSource);
        final ChannelsInteractor interactor = new ChannelsInteractorImpl(repository);

        return new ChannelsPresenter(interactor);
    }
}
