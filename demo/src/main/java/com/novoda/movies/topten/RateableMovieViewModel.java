package com.novoda.movies.topten;

import android.support.annotation.DrawableRes;

interface RateableMovieViewModel {

    long id();

    String title();

    float rating();

    boolean liked();

    @DrawableRes
    int poster();

    UserActions actions();

    interface UserActions {

        void onSelectMovie();

        void onToggleLike();

        void onRate(float rating);
    }
}
