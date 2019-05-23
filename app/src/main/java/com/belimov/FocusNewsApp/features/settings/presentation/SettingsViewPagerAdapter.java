package com.belimov.FocusNewsApp.features.settings.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.channels.presentation.ChannelsFragment;
import com.belimov.FocusNewsApp.features.settings.loader.UpdateTimeFragment;

public class SettingsViewPagerAdapter extends FragmentStatePagerAdapter {

    static final int CHANNELS_FRAGMENT = 0;
    private static final int UPDATE_TIME_FRAGMENT = 1;

    private final int FRAGMENTS_COUNT = 2;
    private final Context context;

    public SettingsViewPagerAdapter(final Context context, final FragmentManager fm) {
        super(fm);

        this.context = context;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
            default:
                throw new IllegalStateException("Неизвестный индекс фрагмента");
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
