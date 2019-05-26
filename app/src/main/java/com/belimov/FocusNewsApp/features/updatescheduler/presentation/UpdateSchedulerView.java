package com.belimov.FocusNewsApp.features.updatescheduler.presentation;

import com.belimov.FocusNewsApp.features.MvpView;

public interface UpdateSchedulerView extends MvpView {
    void setControls(boolean autoupdateState, int days, int hours, int minutes);
}
