package com.novoda.movies.rateable;

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
        posterImageView.setImageResource(viewModel.poster);
        titleTextView.setText(viewModel.title);
        likeImageView.setImageResource(viewModel.liked ? R.drawable.ic_favorite_24dp : R.drawable.ic_favorite_border_24dp);
        ratingBar.setOnRatingBarChangeListener(null);
        ratingBar.setRating(viewModel.rating);
        Actions actions = collateActionsFor(viewModel);
        ActionsAccessibilityDelegate a11yDelegate = new ActionsAccessibilityDelegate(itemView.getResources(), actions);
        ViewCompat.setAccessibilityDelegate(itemView, a11yDelegate);
        itemView.setContentDescription(viewModel.title + ", rating " + viewModel.rating + ", liked: " + viewModel.liked);

        if (a11yServices.isSpokenFeedbackEnabled() || a11yServices.isSwitchAccessEnabled()) {
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

    private void bindForIndirectAccess(Actions actions) {
        itemView.setOnClickListener(v -> new ActionsAlertDialogCreator(itemView.getContext()).create(actions).show());
        likeImageView.setClickable(false);
        ratingBar.setOnRatingBarChangeListener(null);
        ratingBar.setIsIndicator(true);
    }

    private Action selectActionFor(RateableMovieViewModel viewModel) {
        return new Action(R.id.action_rateable_movie_click_select, R.string.action_rateable_movie_click_select, () -> viewModel.actions.onSelectMovie());
    }

    private Action toggleLikeActionFor(RateableMovieViewModel viewModel) {
        Runnable onToggleLikeRunnable = () -> viewModel.actions.onToggleLike();
        if (viewModel.liked) {
            return new Action(R.id.action_rateable_movie_click_toggle_like, R.string.action_rateable_movie_click_remove_like, onToggleLikeRunnable);
        } else {
            return new Action(R.id.action_rateable_movie_click_toggle_like, R.string.action_rateable_movie_click_like, onToggleLikeRunnable);
        }
    }

    private Action rateActionFor(RateableMovieViewModel viewModel) {
        return new Action(R.id.action_rateable_movie_click_rate, R.string.action_rateable_movie_click_rate, () -> showRatingDialog(viewModel));
    }

    private void showRatingDialog(RateableMovieViewModel viewModel) {
        ActionsAlertDialogCreator actionsAlertDialogCreator = new ActionsAlertDialogCreator(itemView.getContext(), R.string.action_rateable_movie_dialog_title_rate_movie);
        actionsAlertDialogCreator.create(new Actions(Arrays.asList(
                new Action(R.id.action_rateable_movie_rate_half_star, R.string.action_rateable_movie_rate_half_star, () -> viewModel.actions.onRate(0.5f)),
                new Action(R.id.action_rateable_movie_rate_one_star, R.string.action_rateable_movie_rate_one_star, () -> viewModel.actions.onRate((float) 1)),
                new Action(R.id.action_rateable_movie_rate_one_half_star, R.string.action_rateable_movie_rate_one_half_star, () -> viewModel.actions.onRate(1.5f)),
                new Action(R.id.action_rateable_movie_rate_two_star, R.string.action_rateable_movie_rate_two_star, () -> viewModel.actions.onRate((float) 2)),
                new Action(R.id.action_rateable_movie_rate_two_half_star, R.string.action_rateable_movie_rate_two_half_star, () -> viewModel.actions.onRate(2.5f)),
                new Action(R.id.action_rateable_movie_rate_three_star, R.string.action_rateable_movie_rate_three_star, () -> viewModel.actions.onRate((float) 3)),
                new Action(R.id.action_rateable_movie_rate_three_half_star, R.string.action_rateable_movie_rate_three_half_star, () -> viewModel.actions.onRate(3.5f)),
                new Action(R.id.action_rateable_movie_rate_four_star, R.string.action_rateable_movie_rate_four_star, () -> viewModel.actions.onRate((float) 4)),
                new Action(R.id.action_rateable_movie_rate_four_half_star, R.string.action_rateable_movie_rate_four_half_star, () -> viewModel.actions.onRate(4.5f)),
                new Action(R.id.action_rateable_movie_rate_five_star, R.string.action_rateable_movie_rate_five_star, () -> viewModel.actions.onRate((float) 5))
        ))).show();
    }

    private void bindForTouchAccess(RateableMovieViewModel viewModel) {
        itemView.setOnClickListener(v -> viewModel.actions.onSelectMovie());
        likeImageView.setOnClickListener(v -> viewModel.actions.onToggleLike());
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            viewModel.actions.onRate(rating);
        });
        ratingBar.setIsIndicator(false);
    }
}
