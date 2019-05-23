package com.belimov.FocusNewsApp.features;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected abstract <T extends MvpView> MvpPresenter<T> getPresenter();

    protected abstract <T extends MvpView> T getMvpView();

    private MvpPresenter<MvpView> presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = getPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(getMvpView());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }
}
