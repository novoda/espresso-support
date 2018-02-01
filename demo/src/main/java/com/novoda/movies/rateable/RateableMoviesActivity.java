package com.novoda.movies.rateable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.novoda.movies.R;

import java.util.ArrayList;
import java.util.List;

public class RateableMoviesActivity extends AppCompatActivity {

    private final FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
    private final RateableMoviesAdapter rateableMoviesAdapter = new RateableMoviesAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateable_movies);

        RecyclerView recyclerView = findViewById(R.id.rateable_movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rateableMoviesAdapter);

        fakeMovieRepository.observeChanges(observer);
    }

    private final FakeMovieRepository.Observer observer = new FakeMovieRepository.Observer() {

        @Override
        public void onUpdated(List<Movie> movies) {
            List<RateableMovieViewModel> viewModels = new ArrayList<>(movies.size());
            for (Movie movie : movies) {
                viewModels.add(convertToViewModel(movie));
            }
            rateableMoviesAdapter.update(viewModels);
        }

        private RateableMovieViewModel convertToViewModel(Movie movie) {
            return new RateableMovieViewModelBuilder(movie.id, movie.title, movie.poster)
                    .rating(movie.rating)
                    .liked(movie.liked)
                    .actions(new RateableMovieViewModel.UserActions() {
                        @Override
                        public void onSelectMovie() {
                            Toast.makeText(RateableMoviesActivity.this, "onSelect: " + movie.title, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onToggleLike() {
                            fakeMovieRepository.onToggleLike(movie.id);
                        }

                        @Override
                        public void onRate(float rating) {
                            fakeMovieRepository.onRate(movie.id, rating);
                        }
                    }).build();
        }
    };
}
