package com.novoda.movies.rateable;

import android.support.annotation.DrawableRes;

import com.novoda.movies.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FakeMovieRepository {

    private final List<Movie> movies = initialFetchFromSomewhere();

    private Observer observer;

    void observeChanges(Observer observer) {
        this.observer = observer;
        observer.onUpdated(movies);
    }

    void onRate(long id, float newRating) {
        List<Movie> updated = new ArrayList<>();
        for (Movie movie : movies) {
            if (id == movie.id) {
                updated.add(new Movie(movie.id, movie.title, newRating, movie.liked, movie.poster));
            } else {
                updated.add(new Movie(movie));
            }
        }
        updateWith(updated);
    }

    void onToggleLike(long id) {
        List<Movie> updated = new ArrayList<>();
        for (Movie movie : movies) {
            if (id == movie.id) {
                updated.add(new Movie(movie.id, movie.title, movie.rating, !movie.liked, movie.poster));
            } else {
                updated.add(new Movie(movie));
            }
        }
        updateWith(updated);
    }

    private void updateWith(List<Movie> updated) {
        movies.clear();
        movies.addAll(updated);
        observer.onUpdated(updated);
    }

    private static List<Movie> initialFetchFromSomewhere() {
        return new ArrayList<>(Arrays.asList(
                createMovie("Arrival", 4.5f, true, R.drawable.movie_arrival),
                createMovie("Interstellar", 4.5f, true, R.drawable.movie_interstellar),
                createMovie("The Royal Tenenbaums", 5, true, R.drawable.movie_royaltenenbaums),
                createMovie("Whiplash", 4.5f, true, R.drawable.movie_whiplash),
                createMovie("Beetlejuice", 5, false, R.drawable.movie_beetlejuice),
                createMovie("Iron Giant", 4.5f, true, R.drawable.movie_irongiant),
                createMovie("Million Dollar Baby", 4.5f, true, R.drawable.movie_milliondollarbaby),
                createMovie("Take Shelter", 4.5f, true, R.drawable.movie_takeshelter),
                createMovie("Planes, Trains and Automobiles", 4, true, R.drawable.movie_planestrainsautomobiles),
                createMovie("Fantastic Mr Fox", 4, false, R.drawable.movie_fantasticmrfox)
        ));
    }

    private static Movie createMovie(String title, float rating, boolean liked, @DrawableRes int poster) {
        return new Movie(title.hashCode(), title, rating, liked, poster);
    }

    interface Observer {

        void onUpdated(List<Movie> viewModels);
    }
}
