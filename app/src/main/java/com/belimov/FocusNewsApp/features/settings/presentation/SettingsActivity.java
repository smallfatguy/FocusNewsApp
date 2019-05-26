package com.belimov.FocusNewsApp.features.settings.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.BaseActivity;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.MvpView;
import com.belimov.FocusNewsApp.features.news.presentation.NewsFeedActivity;
import com.google.android.material.tabs.TabLayout;

public class SettingsActivity extends BaseActivity implements SettingsView {

    private SettingsPresenter presenter;

    private EditText feedUrlInput;
    private Button addFeed;
    private ProgressBar loadingProgressBar;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected MvpPresenter<SettingsView> getPresenter() {
        presenter = SettingsPresenterFactory.createPresenter(this);
        return presenter;
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String link = getIntent().getDataString();
        if (link != null && !link.isEmpty()) {
            feedUrlInput.setText(link);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initView();
        initTabs();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.saveChannelInput(getTextFromField());
    }

    @Override
    public String getTextFromField() {
        return feedUrlInput.getText().toString();
    }

    public void initView() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        feedUrlInput = findViewById(R.id.feed_url_input);
        addFeed = findViewById(R.id.add_feed);
        loadingProgressBar = findViewById(R.id.loading_bar);
        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.uploadChannel();
            }
        });
    }

    public void initTabs() {
        viewPager = findViewById(R.id.settings_view_pager);
        tabLayout = findViewById(R.id.settings_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.channels_tab_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.update_time_tab_title));

        final FragmentStatePagerAdapter viewPagerAdapter = new SettingsViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());

        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                presenter.saveCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        returnToNews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            returnToNews();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setChannelInput(final String oldLink) {
        clearChannelInput();
        String link = getIntent().getDataString();
        if (link == null || link.isEmpty()) {
            link = oldLink;
        }
        feedUrlInput.setText(link);
    }

    @Override
    public void showLoading() {
        loadingProgressBar.bringToFront();
        loadingProgressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void clearChannelInput() {
        feedUrlInput.setText("");
    }

    @Override
    public void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getStringResource(int id) {
        return getResources().getString(id);
    }

    @Override
    public void refreshViewPager() {
        viewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (view != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void setCurrentTab(final int tab) {
        viewPager.setCurrentItem(tab);
    }

    private void returnToNews() {
        Intent intent = new Intent(this, NewsFeedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
