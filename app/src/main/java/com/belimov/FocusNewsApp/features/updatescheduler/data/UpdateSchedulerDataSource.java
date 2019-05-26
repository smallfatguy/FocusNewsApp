package com.belimov.FocusNewsApp.features.updatescheduler.data;

public interface UpdateSchedulerDataSource {
    void saveTime(int days, int hours, int minutes);

    Integer[] getTime();

    void saveAutoupdateState(boolean state);

    boolean getAutoupdateState();
}
