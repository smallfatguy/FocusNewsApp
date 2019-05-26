package com.belimov.FocusNewsApp.features.updatescheduler.presentation;

import android.content.Context;

import com.belimov.FocusNewsApp.features.updatescheduler.data.UpdateSchedulerDataSource;
import com.belimov.FocusNewsApp.features.updatescheduler.data.UpdateSchedulerDataSourceImpl;
import com.belimov.FocusNewsApp.features.updatescheduler.data.UpdateSchedulerRepositoryImpl;

public class UpdateSchedullerPresenterFactory {

    static UpdateSchedulerPresenter createPresenter(final Context context) {

        final UpdateSchedulerDataSource dataSource = new UpdateSchedulerDataSourceImpl(context);
        final UpdateSchedulerRepository repository = new UpdateSchedulerRepositoryImpl(dataSource);

        return new UpdateSchedulerPresenter(repository);
    }
}
