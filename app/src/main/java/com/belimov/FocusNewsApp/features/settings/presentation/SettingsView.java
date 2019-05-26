package com.belimov.FocusNewsApp.features.settings.presentation;

import com.belimov.FocusNewsApp.features.MvpView;

interface SettingsView extends MvpView {

    String getTextFromField();

    void showLoading();

    void hideLoading();

    void clearChannelInput();

    void showToast(String message);

    String getStringResource(int id);

    void refreshViewPager();

    void hideKeyboard();

    void setCurrentTab(int tab);

    void setChannelInput(String oldLink);
}
