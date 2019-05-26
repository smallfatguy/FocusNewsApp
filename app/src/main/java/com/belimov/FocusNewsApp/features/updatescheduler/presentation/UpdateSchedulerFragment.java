package com.belimov.FocusNewsApp.features.updatescheduler.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.BaseFragment;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.MvpView;

public class UpdateSchedulerFragment extends BaseFragment implements UpdateSchedulerView {

    private NumberPicker daysPicker;
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;

    private Switch isAutoupdateEnabled;

    private UpdateSchedulerPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_time_fragment, container, false);
    }

    @Override
    protected MvpPresenter<UpdateSchedulerView> getPresenter() {
        presenter = UpdateSchedullerPresenterFactory.createPresenter(this.getContext());
        return presenter;
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isAutoupdateEnabled = getView().findViewById(R.id.auto_update_switch);

        daysPicker = getView().findViewById(R.id.day_period_picker);
        hoursPicker = getView().findViewById(R.id.hour_period_picker);
        minutesPicker = getView().findViewById(R.id.minutes_period_picker);

        onSwitchClick();
    }

    private void initNumberPicker(final NumberPicker numberPicker, final int minValue, final int maxValue, final int currentValue) {
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(currentValue);

        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                collectTimeFromPickersToPrefs();

                if (isAutoupdateEnabled.isChecked()) {
                    presenter.enqueueAutoupdateWorker();
                }
            }
        });
    }

    private void onSwitchClick() {
        isAutoupdateEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveAutoupdateState(isAutoupdateEnabled.isChecked());

                if (isAutoupdateEnabled.isChecked()) {
                    collectTimeFromPickersToPrefs();
                    presenter.enqueueAutoupdateWorker();
                } else {
                    presenter.cancelAutoupdateWorker();
                }

            }
        });
    }

    private void collectTimeFromPickersToPrefs() {
        presenter.saveTime(daysPicker.getValue(), hoursPicker.getValue(), minutesPicker.getValue());
    }

    @Override
    public void setControls(boolean autoupdateState, int days, int hours, int minutes) {
        isAutoupdateEnabled.setChecked(autoupdateState);
        initNumberPicker(daysPicker, 0, 10, days);
        initNumberPicker(hoursPicker, 0, 24, hours);
        initNumberPicker(minutesPicker, 15, 60, minutes);
    }
}
