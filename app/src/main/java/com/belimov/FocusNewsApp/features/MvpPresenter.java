package com.belimov.FocusNewsApp.features;

public class MvpPresenter<View extends MvpView> {

    protected View view;

    public void attachView(View view) {
        this.view = view;
        onViewReady();
    }

    protected void onViewReady() {
    }

    public void detachView() {
        view = null;
    }
}
