package com.novoda.movies.topten;

import android.support.annotation.DrawableRes;

import com.novoda.movies.R;

class RateableMovieViewModelFixtures {

    private long id = "Arrival".hashCode();
    private String title = "Arrival";
    private float rating = 5;
    private boolean liked = true;
    private int poster = R.drawable.topten_arrival;
    private RateableMovieViewModel.UserActions userActions = new RateableMovieViewModel.UserActions() {
        @Override
        public void selectMovie() {

        }

        @Override
        public void toggleLike() {

        }

        @Override
        public void rate(float rating) {

        }
    };

    static RateableMovieViewModelFixtures viewModel(RateableMovieViewModel.UserActions userActions) {
        RateableMovieViewModelFixtures fixtures = new RateableMovieViewModelFixtures();
        fixtures.userActions = userActions;
        return fixtures;
    }

    static RateableMovieViewModelFixtures viewModel() {
        return new RateableMovieViewModelFixtures();
    }

    RateableMovieViewModelFixtures id(long id) {
        this.id = id;
        return this;
    }

    RateableMovieViewModelFixtures title(String title) {
        this.title = title;
        return this;
    }

    RateableMovieViewModelFixtures rating(float rating) {
        this.rating = rating;
        return this;
    }

    RateableMovieViewModelFixtures liked(boolean liked) {
        this.liked = liked;
        return this;
    }

    RateableMovieViewModelFixtures poster(@DrawableRes int poster) {
        this.poster = poster;
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
            public UserActions onUser() {
                return userActions;
            }
        };
    }
}
