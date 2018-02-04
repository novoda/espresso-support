package com.novoda.movies.rateable;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.RatingBar;

import com.novoda.espresso.ViewTestRule;
import com.novoda.movies.R;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.novoda.espresso.ViewTestRule.underTest;
import static com.novoda.movies.rateable.RateableMovieViewModelFixtures.viewModel;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TouchMode_RateableMovieViewHolderTest {

    @Rule
    public ViewTestRule viewTestRule = new ViewTestRule(R.layout.item_view_rateable_movie);

    private RateableMovieViewHolder rateableMovieViewHolder;
    private RateableMovieViewModel.UserActions userActions = mock(RateableMovieViewModel.UserActions.class);

    @Before
    public void setUp() {
        rateableMovieViewHolder = new RateableMovieViewHolder(viewTestRule.getView());
    }

    @Test
    public void bindsTitle() {
        RateableMovieViewModel viewModel = viewModel().title("Arrival").rating(5).liked(true).build();

        rateableMovieViewHolder.bind(viewModel);

        onView(withId(R.id.item_rateable_text_title)).check(matches(withText("Arrival")));
    }

    @Test
    public void bindsOnSelectAction() {
        RateableMovieViewModel viewModel = viewModel(userActions).build();
        rateableMovieViewHolder.bind(viewModel);

        onView(underTest()).perform(click());

        verify(userActions).onSelectMovie();
    }

    @Test
    public void bindsOnToggleLikeAction() {
        RateableMovieViewModel viewModel = viewModel(userActions).build();
        rateableMovieViewHolder.bind(viewModel);

        onView(withId(R.id.item_rateable_image_like)).perform(click());

        verify(userActions).onToggleLike();
    }

    @Test
    public void bindsOnRateAction() {
        RateableMovieViewModel viewModel = viewModel(userActions).build();
        rateableMovieViewHolder.bind(viewModel);

        onView(withId(R.id.item_rateable_rating)).perform(setRating(4.5f));

        verify(userActions).onRate(4.5f);
    }

    private static ViewAction setRating(float rating) {
        if (rating % 0.5 != 0) {
            throw new IllegalArgumentException("Rating must be multiple of 0.5f");
        }

        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RatingBar.class);
            }

            @Override
            public String getDescription() {
                return "Set rating on RatingBar in 0.5f increments";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((RatingBar) view).setRating(rating);
            }
        };
    }
}
