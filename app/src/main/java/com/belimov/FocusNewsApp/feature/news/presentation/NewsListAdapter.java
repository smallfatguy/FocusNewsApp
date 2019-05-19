package com.belimov.FocusNewsApp.feature.news.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.feature.news.domain.model.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<News> news = new ArrayList<News>();
    private OnItemClickListener listener;
    private Context context;

    public NewsListAdapter(final Context context, final OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    public void setNewsList(List<News> newsList) {
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
        holder.bind(news.get(position), listener, context);
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

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitleView = itemView.findViewById(R.id.news_title);
            pubDateView = itemView.findViewById(R.id.news_pubdate);
            channel = itemView.findViewById(R.id.news_channel);
            description = itemView.findViewById(R.id.news_description);

            newsViewGroup = itemView.findViewById(R.id.news_item_layout);
            expandableView = itemView.findViewById(R.id.expand_card_view);

            openInBrowserButton = itemView.findViewById(R.id.open_in_browser_button);
        }

        public void bind(final News news, final OnItemClickListener listener, final Context context) {
            final String pubDate = new SimpleDateFormat("dd/MM/yyyy").format(news.getPubDate());

            newsTitleView.setText(news.getTitle());
            pubDateView.setText(pubDate);
            channel.setText(news.getChannelTitle());

            description.setText(news.getDescription());

            newsViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(news);
//                    TransitionManager.beginDelayedTransition((ViewGroup) description.getRootView(), new AutoTransition());
                    if (description.getVisibility() == View.GONE) {
                        description.setVisibility(View.VISIBLE);
                        openInBrowserButton.setVisibility(View.VISIBLE);
                        expandableView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    } else {
                        description.setVisibility(View.GONE);
                        openInBrowserButton.setVisibility(View.GONE);
                        expandableView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    }
                }
            });

            openInBrowserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(news);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(news.getNewsLink()));
                    context.startActivity(i);
                }
            });
        }
    }
}
