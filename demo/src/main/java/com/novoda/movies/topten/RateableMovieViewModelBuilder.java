package com.novoda.movies.topten;

import android.support.annotation.DrawableRes;

class RateableMovieViewModelBuilder {

    private long id;
    private String title;
    private float rating;
    private boolean liked;
    private int poster;
    private RateableMovieViewModel.UserActions actions;

    RateableMovieViewModelBuilder() {
    }

    RateableMovieViewModelBuilder(RateableMovieViewModel viewModel) {
        this.id = viewModel.id();
        this.title = viewModel.title();
        this.rating = viewModel.rating();
        this.liked = viewModel.liked();
        this.poster = viewModel.poster();
        this.actions = viewModel.actions();
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
        return new RateableMovieViewModel() {
            @Override
            public long id() {
                return id;
            }

            @Override
            public String title() {
                return title;
            }

            @Override
            public float rating() {
                return rating;
            }

            @Override
            public boolean liked() {
                return liked;
            }

            @Override
            public int poster() {
                return poster;
            }

            @Override
            public UserActions actions() {
                return actions;
            }
        };
    }
}
