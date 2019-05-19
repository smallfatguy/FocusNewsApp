package com.belimov.FocusNewsApp.feature.news.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.feature.settings.presentation.SettingsActivity;

public class NewsFeedActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_activity);
        final NewsFeedFragment fragment = new NewsFeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    public void startSettingsActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
