package com.belimov.FocusNewsApp.features.updatescheduler.presentation;

public interface UpdateSchedulerRepository {
    void saveTime(int days, int hours, int minutes);

    Integer[] loadTime();

    void saveAutoupdateState(boolean state);

    boolean loadAutoupdateState();

    int calculateTime();
}
