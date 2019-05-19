package com.belimov.FocusNewsApp.feature.settings.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belimov.FocusNewsApp.App;
import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.db.entities.ChannelDbEntity;
import com.belimov.FocusNewsApp.feature.settings.data.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsFragment extends Fragment {

    MutableLiveData<List<ChannelDbEntity>> channelsList = new MutableLiveData<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                channelsList.postValue(App.getDB().channelsDao().getChannels());
            }
        }).start();

        RecyclerView recyclerView = getView().findViewById(R.id.channels_list);
        final ChannelsListAdapter adapter = new ChannelsListAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        channelsList.observe(this, new Observer<List<ChannelDbEntity>>() {
            @Override
            public void onChanged(List<ChannelDbEntity> channelDbEntities) {

                adapter.setChannels(convert(channelDbEntities));
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.channels_fragment, container, false);
    }

    private List<Channel> convert(final List<ChannelDbEntity> list) {
        List<Channel> channels = new ArrayList<>();
        for (final ChannelDbEntity channelDbEntity : list) {
            channels.add(new Channel(channelDbEntity.url, channelDbEntity.title, channelDbEntity.link, channelDbEntity.isActive));
        }
        return channels;
    }
}
