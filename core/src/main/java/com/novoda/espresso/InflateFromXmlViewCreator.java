package com.novoda.espresso;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class InflateFromXmlViewCreator<T extends View> implements ViewCreator<T> {

    @LayoutRes
    private final int id;

    InflateFromXmlViewCreator(@LayoutRes int id) {
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T createView(Context context, ViewGroup parentView) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return (T) layoutInflater.inflate(id, parentView, false);
    }
}
