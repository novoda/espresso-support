package com.novoda.movies.rateable;

import android.support.annotation.DrawableRes;

class Movie {

    final long id;
    final String title;
    final float rating;
    final boolean liked;
    @DrawableRes
    final int poster;

    Movie(long id, String title, float rating, boolean liked, int poster) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.liked = liked;
        this.poster = poster;
    }

    Movie(Movie movie) {
        this(movie.id, movie.title, movie.rating, movie.liked, movie.poster);
    }
}
