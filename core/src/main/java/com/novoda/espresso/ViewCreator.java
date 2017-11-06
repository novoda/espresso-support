package com.novoda.espresso;

import android.view.View;

public interface ViewCreator<T extends View> {

    T createView();
}
