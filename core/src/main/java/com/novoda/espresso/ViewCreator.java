package com.novoda.espresso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface ViewCreator<T extends View> {

    /**
     * Create the view, inflating or programmatically. Do not attach the created view to the parentView.
     *
     * @param context    Activity Context
     * @param parentView Activity root content View
     */
    T createView(Context context, ViewGroup parentView);
}
