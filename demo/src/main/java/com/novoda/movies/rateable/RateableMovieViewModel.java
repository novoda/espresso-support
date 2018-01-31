package com.novoda.movies.rateable;

import android.support.annotation.DrawableRes;

class RateableMovieViewModel {

    final long id;
    final String title;
    final float rating;
    final boolean liked;
    @DrawableRes
    final int poster;
    final UserActions actions;

    RateableMovieViewModel(long id, String title, float rating, boolean liked, int poster, UserActions actions) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.liked = liked;
        this.poster = poster;
        this.actions = actions;
    }

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
