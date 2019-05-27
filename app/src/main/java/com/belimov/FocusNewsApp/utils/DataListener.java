package com.belimov.FocusNewsApp.utils;

public interface DataListener<T> {
    void onChanged(T data);
}
