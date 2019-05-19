package com.belimov.FocusNewsApp.feature.settings.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.feature.news.data.RssFeedParser;
import com.belimov.FocusNewsApp.feature.news.presentation.NewsFeedActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initTabs();

        final EditText feedUrlInput = findViewById(R.id.feed_url_input);
        Button addFeed = findViewById(R.id.add_feed);

        String link = getIntent().getDataString();
        if (link != null && !link.isEmpty()) {
            feedUrlInput.setText(link);
        }

        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RssFeedParser.parseFeed(String.valueOf(feedUrlInput.getText()));
                    }
                }).start();
            }
        });

    }

    private void initTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.settings_view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.settings_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.channels_tab_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.update_time_tab_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.appearance_tab_title));

        final FragmentStatePagerAdapter viewPagerAdapter = new SettingsViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());

        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, NewsFeedActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
