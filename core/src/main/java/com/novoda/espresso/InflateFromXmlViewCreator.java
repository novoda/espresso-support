package com.novoda.espresso;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

class InflateFromXmlViewCreator<T extends View> implements ViewCreator<T> {

    @LayoutRes
    private final int id;
    private final LayoutInflater layoutInflater;

    InflateFromXmlViewCreator(@LayoutRes int id, LayoutInflater layoutInflater) {
        this.id = id;
        this.layoutInflater = layoutInflater;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T createView() {
        return (T) layoutInflater.inflate(id, null);
    }
}
