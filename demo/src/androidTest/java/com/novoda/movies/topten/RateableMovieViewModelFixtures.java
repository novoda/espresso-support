package com.novoda.movies.topten;

import com.novoda.movies.R;

class RateableMovieViewModelFixtures {

    static RateableMovieViewModelBuilder viewModel(RateableMovieViewModel.UserActions userActions) {
        return viewModel().actions(userActions);
    }

    static RateableMovieViewModelBuilder viewModel() {
        return new RateableMovieViewModelBuilder()
                .id("Arrival".hashCode())
                .title("Arrival")
                .rating(5)
                .liked(true)
                .poster(R.drawable.topten_arrival)
                .actions(new RateableMovieViewModel.UserActions() {
                    @Override
                    public void onSelectMovie() {
                    }

                    @Override
                    public void onToggleLike() {
                    }

                    @Override
                    public void onRate(float rating) {
                    }
                });
    }
}
