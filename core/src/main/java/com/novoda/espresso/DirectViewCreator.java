package com.novoda.espresso;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

final class DirectViewCreator<T extends View> implements ViewCreator<T> {

    private final T view;

    DirectViewCreator(final T view) {
        this.view = view;
    }

    @Override
    public T createView(final Context ignored, final ViewGroup parent) {
        view.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        return view;
    }
}
