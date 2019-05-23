package com.belimov.FocusNewsApp.features.settings.loader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.utils.PreferenceUtils;

public class UpdateTimeFragment extends Fragment {

    private final static String AUTOUPDATE_SETTING_PREF = "autoupdate";

    private final static String AUTOUPDATE_DAYS_PREF = "autoupdate_days";
    private final static String AUTOUPDATE_HOURS_PREF = "autoupdate_hours";
    private final static String AUTOUPDATE_MINUTES_PREF = "autoupdate_minutes";

    private NumberPicker daysPicker;
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;

    private Switch isAutoupdateEnabled;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_time_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        onSwitchClick();
    }

    private void initView() {
        isAutoupdateEnabled = getView().findViewById(R.id.auto_update_switch);

        daysPicker = getView().findViewById(R.id.day_period_picker);
        hoursPicker = getView().findViewById(R.id.hour_period_picker);
        minutesPicker = getView().findViewById(R.id.minutes_period_picker);

        isAutoupdateEnabled.setChecked(PreferenceUtils.loadBoolean(getContext(), AUTOUPDATE_SETTING_PREF, false));
        initNumberPicker(daysPicker, 0, 10, PreferenceUtils.loadInteger(getContext(), AUTOUPDATE_DAYS_PREF, 0));
        initNumberPicker(hoursPicker, 0, 24, PreferenceUtils.loadInteger(getContext(), AUTOUPDATE_HOURS_PREF, 0));
        initNumberPicker(minutesPicker, 15, 60, PreferenceUtils.loadInteger(getContext(), AUTOUPDATE_MINUTES_PREF, 15));

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
                    enqueueAutoupdateWorker();
                }
            }
        });
    }

    private void onSwitchClick() {
        isAutoupdateEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveBoolean(getContext(), AUTOUPDATE_SETTING_PREF, isAutoupdateEnabled.isChecked());

                if (isAutoupdateEnabled.isChecked()) {
                    collectTimeFromPickersToPrefs();
                    enqueueAutoupdateWorker();
                } else {
                    cancelAutoupdateWorker();
                }

            }
        });
    }

    private void enqueueAutoupdateWorker() {
        final PeriodicWorkRequest request = LoaderWorker.createPeriodicWork(calculateTime());
        WorkManager.getInstance().enqueueUniquePeriodicWork(LoaderWorker.AUTOUPDATE_TAG, ExistingPeriodicWorkPolicy.REPLACE, request);
    }

    private void collectTimeFromPickersToPrefs() {
        PreferenceUtils.saveInteger(getContext(), AUTOUPDATE_DAYS_PREF, daysPicker.getValue());
        PreferenceUtils.saveInteger(getContext(), AUTOUPDATE_HOURS_PREF, hoursPicker.getValue());
        PreferenceUtils.saveInteger(getContext(), AUTOUPDATE_MINUTES_PREF, minutesPicker.getValue());
    }

    private int calculateTime() {
        return PreferenceUtils.loadInteger(getContext(), AUTOUPDATE_DAYS_PREF, daysPicker.getValue()) * 1440
                + PreferenceUtils.loadInteger(getContext(), AUTOUPDATE_HOURS_PREF, daysPicker.getValue()) * 60
                + PreferenceUtils.loadInteger(getContext(), AUTOUPDATE_MINUTES_PREF, daysPicker.getValue());
    }

    private void cancelAutoupdateWorker() {
        WorkManager.getInstance().cancelAllWorkByTag(LoaderWorker.AUTOUPDATE_TAG);
    }
}
