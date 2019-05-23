package com.belimov.FocusNewsApp.features.news.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.BaseFragment;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.MvpView;
import com.belimov.FocusNewsApp.features.news.domain.model.News;

import java.util.List;

public class NewsFeedFragment extends BaseFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    private NewsPresenter presenter;
    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private View emptyListMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_feed_fragment, container, false);
    }

    @Override
    protected MvpPresenter<NewsView> getPresenter() {
        presenter = NewsPresenterFactory.createPresenter();
        return presenter;
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyListMessage = getView().findViewById(R.id.empty_list_message);
        refreshLayout = getView().findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = getView().findViewById(R.id.news_list);
        adapter = new NewsListAdapter(new NewsListAdapter.ItemClickListener() {
            @Override
            public void onNewsClick(final String guid, final boolean isCollapsed) {
                if (isCollapsed) {
                    presenter.expandNews(guid);
                } else {
                    presenter.collapseNews(guid);
                }
            }

            @Override
            public void onOpenBrowserClick(final String link) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                getContext().startActivity(i);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void showNews(List<News> news) {
        adapter.setNewsList(news);
    }

    @Override
    public void showMessage() {
        emptyListMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessage() {
        emptyListMessage.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getStringResource(int id) {
        return getResources().getString(id);
    }

    @Override
    public void onRefresh() {
        presenter.refreshAllNews();
    }
}
