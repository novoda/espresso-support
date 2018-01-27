package com.novoda.movies.topten;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.novoda.movies.R;

class RateableMovieViewHolder extends RecyclerView.ViewHolder {

    public static RateableMovieViewHolder inflate(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_rateable_movie, parent, false);
        return new RateableMovieViewHolder(view);
    }

    private final ImageView posterImageView;
    private final TextView titleTextView;
    private final ImageView likeImageView;
    private final RatingBar ratingBar;

    RateableMovieViewHolder(View itemView) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.item_rateable_image_poster);
        titleTextView = itemView.findViewById(R.id.item_rateable_text_title);
        likeImageView = itemView.findViewById(R.id.item_rateable_image_like);
        ratingBar = itemView.findViewById(R.id.item_rateable_rating);
    }

    void bind(final RateableMovieViewModel viewModel) {
        posterImageView.setImageResource(viewModel.poster());
        titleTextView.setText(viewModel.title());
        likeImageView.setImageResource(viewModel.liked() ? R.drawable.ic_favorite_24dp : R.drawable.ic_favorite_border_24dp);
        ratingBar.setRating(viewModel.rating());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onUser().selectMovie();
            }
        });
    }
}
