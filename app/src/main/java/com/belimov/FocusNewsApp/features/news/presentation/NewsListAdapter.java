package com.belimov.FocusNewsApp.features.news.presentation;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.news.domain.model.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<News> news = new ArrayList<>();
    private ItemClickListener listener;

    NewsListAdapter(final ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {

        void onNewsClick(String guid, boolean state);

        void onOpenBrowserClick(String link);
    }

    void setNewsList(List<News> newsList) {
        news.clear();
        news.addAll(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(news.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup newsViewGroup;

        private TextView newsTitleView;
        private TextView pubDateView;
        private TextView channel;
        private TextView description;

        private ImageView expandableView;

        private Button openInBrowserButton;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitleView = itemView.findViewById(R.id.news_title);
            pubDateView = itemView.findViewById(R.id.news_pubdate);
            channel = itemView.findViewById(R.id.news_channel);
            description = itemView.findViewById(R.id.news_description);

            newsViewGroup = itemView.findViewById(R.id.news_item_layout);
            expandableView = itemView.findViewById(R.id.expand_card_view);

            openInBrowserButton = itemView.findViewById(R.id.open_in_browser_button);
        }

        void bind(final News news, final ItemClickListener listener) {
            final String pubDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH).format(news.getPubDate());

            collapseNewsCard();

            newsTitleView.setText(news.getTitle());
            pubDateView.setText(pubDate);
            channel.setText(news.getChannelTitle());

            if (!news.isCollapsed()) {
                expandNewsCard(news);
            }

            newsViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (news.isCollapsed()) {
                        expandNewsCard(news);
                    } else {
                        collapseNewsCard();
                    }
                    listener.onNewsClick(news.getGuid(), news.isCollapsed());
                }
            });

            openInBrowserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onOpenBrowserClick(news.getNewsLink());
                }
            });
        }

        private void collapseNewsCard() {
            description.setVisibility(View.GONE);
            openInBrowserButton.setVisibility(View.GONE);
            expandableView.setImageResource(R.drawable.ic_expand_more_black_24dp);

            description.setText("");
        }

        private void expandNewsCard(final News news) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                description.setText(Html.fromHtml(news.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                description.setText(Html.fromHtml(news.getDescription()));
            }
            description.setVisibility(View.VISIBLE);
            openInBrowserButton.setVisibility(View.VISIBLE);
            expandableView.setImageResource(R.drawable.ic_expand_less_black_24dp);
        }
    }
}
