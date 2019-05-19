package com.belimov.FocusNewsApp.feature.settings.presentation;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.belimov.FocusNewsApp.R;

public class SettingsViewPagerAdapter extends FragmentStatePagerAdapter {

    static final int CHANNELS_FRAGMENT = 0;
    private static final int UPDATE_TIME_FRAGMENT = 1;
    private static final int APPEARANCE_SETTINGS_FRAGMENT = 2;

    private final int FRAGMENTS_COUNT = 3;
    private final Context context;

    public SettingsViewPagerAdapter(final Context context, final FragmentManager fm) {
        super(fm);

        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final int resource;
        switch (position) {
            case CHANNELS_FRAGMENT:
                resource = R.string.channels_tab_title;
                break;
            case UPDATE_TIME_FRAGMENT:
                resource = R.string.update_time_tab_title;
                break;
            case APPEARANCE_SETTINGS_FRAGMENT:
                resource = R.string.appearance_tab_title;
                break;
            default:
                throw new IllegalStateException("Неизвестный индекс фрагмента");
        }

        return context.getString(resource);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CHANNELS_FRAGMENT:
                return new ChannelsFragment();
            case UPDATE_TIME_FRAGMENT:
                return new UpdateTimeFragment();
            case APPEARANCE_SETTINGS_FRAGMENT:
                return new AppearanceSettingsFragment();
            default:
                throw new IllegalStateException("Неизвестный индекс фрагмента");
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
