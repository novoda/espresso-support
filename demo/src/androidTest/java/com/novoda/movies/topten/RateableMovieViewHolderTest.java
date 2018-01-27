package com.novoda.movies.topten;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.novoda.espresso.ViewTestRule;
import com.novoda.movies.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.novoda.espresso.ViewTestRule.underTest;
import static com.novoda.movies.topten.RateableMovieViewModelFixtures.viewModel;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RateableMovieViewHolderTest {

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
    public void bindsOnClickItemView() {
        RateableMovieViewModel viewModel = viewModel(userActions).build();
        rateableMovieViewHolder.bind(viewModel);

        onView(underTest()).perform(click());

        verify(userActions).selectMovie();
    }
}
