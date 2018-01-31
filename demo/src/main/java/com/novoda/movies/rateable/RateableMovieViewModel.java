package com.novoda.movies.rateable;

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

        UserActions NO_OP = new UserActions() {
            @Override
            public void onSelectMovie() {
            }

            @Override
            public void onToggleLike() {
            }

            @Override
            public void onRate(float rating) {
            }
        };

        void onSelectMovie();

        void onToggleLike();

        void onRate(float rating);
    }
}
