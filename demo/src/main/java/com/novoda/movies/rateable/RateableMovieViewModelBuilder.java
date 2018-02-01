package com.novoda.movies.rateable;

import android.support.annotation.DrawableRes;

class RateableMovieViewModelBuilder {

    private long id;
    private String title;
    private float rating;
    private boolean liked;
    private int poster;
    private RateableMovieViewModel.UserActions actions;

    RateableMovieViewModelBuilder(long id, String title, @DrawableRes int poster) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.liked = false;
        this.rating = 0;
        this.actions = RateableMovieViewModel.UserActions.NO_OP;
    }

    RateableMovieViewModelBuilder id(long id) {
        this.id = id;
        return this;
    }

    RateableMovieViewModelBuilder title(String title) {
        this.title = title;
        return this;
    }

    RateableMovieViewModelBuilder rating(float rating) {
        this.rating = rating;
        return this;
    }

    RateableMovieViewModelBuilder liked(boolean liked) {
        this.liked = liked;
        return this;
    }

    RateableMovieViewModelBuilder poster(@DrawableRes int poster) {
        this.poster = poster;
        return this;
    }

    RateableMovieViewModelBuilder actions(RateableMovieViewModel.UserActions actions) {
        this.actions = actions;
        return this;
    }

    RateableMovieViewModel build() {
        return new RateableMovieViewModel(id, title, rating, liked, poster, actions);
    }
}
