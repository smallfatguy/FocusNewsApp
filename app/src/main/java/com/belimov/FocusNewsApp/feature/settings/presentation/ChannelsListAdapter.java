package com.belimov.FocusNewsApp.feature.settings.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.feature.settings.data.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsListAdapter extends RecyclerView.Adapter<ChannelsListAdapter.ChannelsViewHolder> {

    private List<Channel> channels = new ArrayList<>();

    public void setChannels(List<Channel> channelsList) {
        channels.clear();
        channels.addAll(channelsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChannelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_item, parent, false);
        return new ChannelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelsViewHolder holder, int position) {
        holder.bind(channels.get(position));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    class ChannelsViewHolder extends RecyclerView.ViewHolder {

        private Switch isChannelActive;
        private TextView channelTitle;
        private View deleteChannel;

        public ChannelsViewHolder(@NonNull View itemView) {
            super(itemView);

            isChannelActive = itemView.findViewById(R.id.is_channel_active);
            channelTitle = itemView.findViewById(R.id.channel_title);
            deleteChannel = itemView.findViewById(R.id.delete_channel);
        }

        public void bind(Channel channel) {
            channelTitle.setText(channel.getTitle());
            isChannelActive.setChecked(channel.isActive());
        }
    }
}



