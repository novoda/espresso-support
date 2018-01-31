package com.novoda.movies.topten;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.novoda.accessibility.AccessibilityServices;
import com.novoda.accessibility.Action;
import com.novoda.accessibility.Actions;
import com.novoda.accessibility.ActionsAccessibilityDelegate;
import com.novoda.accessibility.ActionsAlertDialogCreator;
import com.novoda.movies.R;

import java.util.Arrays;

class RateableMovieViewHolder extends RecyclerView.ViewHolder {

    static RateableMovieViewHolder inflate(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_rateable_movie, parent, false);
        return new RateableMovieViewHolder(view);
    }

    private final ImageView posterImageView;
    private final TextView titleTextView;
    private final ImageView likeImageView;
    private final RatingBar ratingBar;
    private final AccessibilityServices a11yServices;

    RateableMovieViewHolder(View itemView) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.item_rateable_image_poster);
        titleTextView = itemView.findViewById(R.id.item_rateable_text_title);
        likeImageView = itemView.findViewById(R.id.item_rateable_image_like);
        ratingBar = itemView.findViewById(R.id.item_rateable_rating);
        a11yServices = AccessibilityServices.newInstance(itemView.getContext());
    }

    void bind(RateableMovieViewModel viewModel) {
        posterImageView.setImageResource(viewModel.poster());
        titleTextView.setText(viewModel.title());
        likeImageView.setImageResource(viewModel.liked() ? R.drawable.ic_favorite_24dp : R.drawable.ic_favorite_border_24dp);
        ratingBar.setRating(viewModel.rating());

        Actions actions = collateActionsFor(viewModel);
        ActionsAccessibilityDelegate a11yDelegate = new ActionsAccessibilityDelegate(itemView.getResources(), actions);
        ViewCompat.setAccessibilityDelegate(itemView, a11yDelegate);
        itemView.setContentDescription(viewModel.title() + ", rating " + viewModel.rating() + ", liked: " + viewModel.liked()); // such a good content description

        if (a11yServices.isSpokenFeedbackEnabled() || a11yServices.isSwitchAccessEnabled() || !itemView.isInTouchMode()) {
            bindForIndirectAccess(actions);
            a11yDelegate.setClickLabel(R.string.action_rateable_movie_usage_hint_click);
        } else {
            bindForTouchAccess(viewModel);
        }
    }

    private Actions collateActionsFor(RateableMovieViewModel viewModel) {
        return new Actions(
                Arrays.asList(
                        selectActionFor(viewModel),
                        toggleLikeActionFor(viewModel),
                        rateActionFor(viewModel)
                )
        );
    }

    private void bindForIndirectAccess(final Actions actions) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionsAlertDialogCreator actionsAlertDialogCreator = new ActionsAlertDialogCreator(itemView.getContext());
                actionsAlertDialogCreator.create(actions).show();
            }
        });

        likeImageView.setClickable(false);
        ratingBar.setIsIndicator(true);
        ratingBar.setOnRatingBarChangeListener(null);
    }

    private void bindForTouchAccess(final RateableMovieViewModel viewModel) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.actions().onSelectMovie();
            }
        });

        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.actions().onToggleLike();
            }
        });

        ratingBar.setIsIndicator(false);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    viewModel.actions().onRate(rating);
                }
            }
        });
    }

    private Action selectActionFor(final RateableMovieViewModel viewModel) {
        return new Action(R.id.action_rateable_movie_click_select, R.string.action_rateable_movie_click_select, new Runnable() {
            @Override
            public void run() {
                viewModel.actions().onSelectMovie();
            }
        });
    }

    private Action toggleLikeActionFor(final RateableMovieViewModel viewModel) {
        Runnable onToggleLikeRunnable = new Runnable() {
            @Override
            public void run() {
                viewModel.actions().onToggleLike();
            }
        };
        if (viewModel.liked()) {
            return new Action(R.id.action_rateable_movie_click_toggle_like, R.string.action_rateable_movie_click_remove_like, onToggleLikeRunnable);
        } else {
            return new Action(R.id.action_rateable_movie_click_toggle_like, R.string.action_rateable_movie_click_like, onToggleLikeRunnable);
        }
    }

    private Action rateActionFor(final RateableMovieViewModel viewModel) {
        return new Action(R.id.action_rateable_movie_click_rate, R.string.action_rateable_movie_click_rate, new Runnable() {
            @Override
            public void run() {
                ActionsAlertDialogCreator actionsAlertDialogCreator = new ActionsAlertDialogCreator(itemView.getContext(), R.string.action_rateable_movie_dialog_title_rate_movie);
                actionsAlertDialogCreator.create(new Actions(Arrays.asList(
                        new Action(R.id.action_rateable_movie_rate_half_star, R.string.action_rateable_movie_rate_half_star, rateMovie(viewModel, 0.5f)),
                        new Action(R.id.action_rateable_movie_rate_one_star, R.string.action_rateable_movie_rate_one_star, rateMovie(viewModel, 1)),
                        new Action(R.id.action_rateable_movie_rate_one_half_star, R.string.action_rateable_movie_rate_one_half_star, rateMovie(viewModel, 1.5f)),
                        new Action(R.id.action_rateable_movie_rate_two_star, R.string.action_rateable_movie_rate_two_star, rateMovie(viewModel, 2)),
                        new Action(R.id.action_rateable_movie_rate_two_half_star, R.string.action_rateable_movie_rate_two_half_star, rateMovie(viewModel, 2.5f)),
                        new Action(R.id.action_rateable_movie_rate_three_star, R.string.action_rateable_movie_rate_three_star, rateMovie(viewModel, 3)),
                        new Action(R.id.action_rateable_movie_rate_three_half_star, R.string.action_rateable_movie_rate_three_half_star, rateMovie(viewModel, 3.5f)),
                        new Action(R.id.action_rateable_movie_rate_four_star, R.string.action_rateable_movie_rate_four_star, rateMovie(viewModel, 4)),
                        new Action(R.id.action_rateable_movie_rate_four_half_star, R.string.action_rateable_movie_rate_four_half_star, rateMovie(viewModel, 4.5f)),
                        new Action(R.id.action_rateable_movie_rate_five_star, R.string.action_rateable_movie_rate_five_star, rateMovie(viewModel, 5))
                ))).show();
            }
        });
    }

    private Runnable rateMovie(final RateableMovieViewModel viewModel, final float rating) {
        return new Runnable() {
            @Override
            public void run() {
                viewModel.actions().onRate(rating);
            }
        };
    }
}
