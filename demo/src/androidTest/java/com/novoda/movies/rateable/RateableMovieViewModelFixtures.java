package com.novoda.movies.rateable;

import com.novoda.movies.R;

class RateableMovieViewModelFixtures {

    static RateableMovieViewModelBuilder viewModel(RateableMovieViewModel.UserActions userActions) {
        return viewModel().actions(userActions);
    }

    static RateableMovieViewModelBuilder viewModel() {
        return new RateableMovieViewModelBuilder("Arrival".hashCode(), "Arrival", R.drawable.movie_arrival)
                .rating(5)
                .liked(true);
    }
}
