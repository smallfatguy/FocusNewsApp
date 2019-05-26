package com.belimov.FocusNewsApp.features.updatescheduler.data;

import com.belimov.FocusNewsApp.features.updatescheduler.presentation.UpdateSchedulerRepository;

public class UpdateSchedulerRepositoryImpl implements UpdateSchedulerRepository {

    private final static int MINUTES_IN_DAY = 1440;
    private final static int MINUTES_IN_HOUR = 60;

    private final UpdateSchedulerDataSource dataSource;

    public UpdateSchedulerRepositoryImpl(UpdateSchedulerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveTime(int days, int hours, int minutes) {
        dataSource.saveTime(days, hours, minutes);
    }

    @Override
    public Integer[] loadTime() {
        return dataSource.getTime();
    }

    @Override
    public void saveAutoupdateState(boolean state) {
        dataSource.saveAutoupdateState(state);
    }

    @Override
    public boolean loadAutoupdateState() {
        return dataSource.getAutoupdateState();
    }

    @Override
    public int calculateTime() {
        final Integer[] time = loadTime();
        return time[0] * MINUTES_IN_DAY + time[1] * MINUTES_IN_HOUR + time[2];
    }
}
