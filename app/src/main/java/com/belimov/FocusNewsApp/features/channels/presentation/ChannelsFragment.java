package com.belimov.FocusNewsApp.features.channels.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.belimov.FocusNewsApp.R;
import com.belimov.FocusNewsApp.features.BaseFragment;
import com.belimov.FocusNewsApp.features.MvpPresenter;
import com.belimov.FocusNewsApp.features.MvpView;
import com.belimov.FocusNewsApp.features.channels.domain.model.Channel;

import java.util.List;

public class ChannelsFragment extends BaseFragment implements ChannelsView {

    private ChannelsPresenter presenter;
    private RecyclerView recyclerView;
    private ChannelsListAdapter adapter;

    @Override
    protected MvpPresenter<ChannelsView> getPresenter() {
        presenter = ChannelsPresenterFactory.createPresenter();
        return presenter;
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.channels_list);
        adapter = new ChannelsListAdapter(new ChannelsListAdapter.OnItemClickListener() {

            @Override
            public void onChangeChannelState(final String url, final boolean state) {
                presenter.onChangeState(url, state);
            }

            @Override
            public void onDeleteChannel(final String url) {
                presenter.deleteChannel(url);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.channels_fragment, container, false);
    }


    @Override
    public void showChannelsList(List<Channel> channels) {
        adapter.setChannels(channels);
    }
}
