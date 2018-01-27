package com.novoda.movies.topten;

import android.support.annotation.DrawableRes;

interface RateableMovieViewModel {

    long id();

    String title();

    float rating();

    boolean liked();

    @DrawableRes
    int poster();

    UserActions onUser();

    interface UserActions {

        void selectMovie();

        void toggleLike();

        void rate(float rating);
    }
}

