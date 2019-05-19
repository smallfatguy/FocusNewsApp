package com.belimov.FocusNewsApp.feature.news.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belimov.FocusNewsApp.App;
import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.db.entities.NewsDbEntity;
import com.belimov.FocusNewsApp.feature.news.domain.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedFragment extends Fragment {

    private NewsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_feed_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final MutableLiveData<List<News>> liveData = new MutableLiveData<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    liveData.postValue(convert(App.getDB().newsDao().getNewsFromActiveChannels()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        RecyclerView recyclerView = getView().findViewById(R.id.news_list);
        adapter = new NewsListAdapter(getContext(), new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getAppContext()));


        liveData.observe( this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                adapter.setNewsList(news);
            }
        });
    }

    private static List<News> convert(List<NewsDbEntity> dbNewsList) {
        List<News> newsList = new ArrayList<>();
        for (final NewsDbEntity news : dbNewsList) {
            newsList.add(new News(news.guid, news.title, news.description, news.newsLink, news.channelTitle, news.pubDate));
        }
        return newsList;
    }

}
