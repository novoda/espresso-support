package com.novoda.movies.rateable;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.novoda.espresso.AccessibilityRules;
import com.novoda.espresso.ViewTestRule;
import com.novoda.movies.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.novoda.espresso.ViewTestRule.underTest;
import static com.novoda.movies.rateable.RateableMovieViewModelFixtures.viewModel;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SwitchAccess_RateableMovieViewHolderTest {

    private ViewTestRule viewTestRule = new ViewTestRule(R.layout.item_view_rateable_movie);

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(AccessibilityRules.createSwitchAccessTestRule()).around(viewTestRule);

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
    public void clickingOnItemViewOpensMenu() {
        RateableMovieViewModel viewModel = viewModel().build();
        rateableMovieViewHolder.bind(viewModel);

        onView(underTest()).perform(click());

        checkSeeMoreOptionsMenuDisplayed();
    }

    @Test
    public void clickingOnItemViewOpensMenuWithUndoLike() {
        RateableMovieViewModel viewModel = viewModel().liked(true).build();
        rateableMovieViewHolder.bind(viewModel);

        onView(underTest()).perform(click());

        checkSeeMoreOptionsMenuWithUndoLikeDisplayed();
    }

    @Test
    public void clickingOnLikeOpensMenu() {
        RateableMovieViewModel viewModel = viewModel().build();
        rateableMovieViewHolder.bind(viewModel);

        onView(withId(R.id.item_rateable_image_like)).perform(click());

        checkSeeMoreOptionsMenuDisplayed();
    }

    @Test
    public void clickingOnRateOpensMenu() {
        RateableMovieViewModel viewModel = viewModel().build();
        rateableMovieViewHolder.bind(viewModel);

        onView(withId(R.id.item_rateable_rating)).perform(click());

        checkSeeMoreOptionsMenuDisplayed();
    }

    @Test
    public void clickingOnRateActionOpensRateMenu() {
        RateableMovieViewModel viewModel = viewModel().build();
        rateableMovieViewHolder.bind(viewModel);
        onView(underTest()).perform(click());

        onView(withText(R.string.action_rateable_movie_click_rate)).perform(click());

        checkRateMenuDisplayed();
    }

    @Test
    public void bindsRateAction() {
        RateableMovieViewModel viewModel = viewModel(userActions).build();
        rateableMovieViewHolder.bind(viewModel);
        onView(underTest()).perform(click());
        onView(withText(R.string.action_rateable_movie_click_rate)).perform(click());

        onView(withText(R.string.action_rateable_movie_rate_one_half_star)).perform(click());

        verify(userActions).onRate(1.5f);
    }

    private void checkRateMenuDisplayed() {
        checkViewsWithTextDisplayed(
                R.string.action_rateable_movie_rate_half_star,
                R.string.action_rateable_movie_rate_one_star,
                R.string.action_rateable_movie_rate_one_half_star,
                R.string.action_rateable_movie_rate_two_star,
                R.string.action_rateable_movie_rate_two_half_star,
                R.string.action_rateable_movie_rate_three_star,
                R.string.action_rateable_movie_rate_three_half_star,
                R.string.action_rateable_movie_rate_four_star,
                R.string.action_rateable_movie_rate_four_half_star,
                R.string.action_rateable_movie_rate_five_star
        );
    }

    private void checkSeeMoreOptionsMenuDisplayed() {
        checkViewsWithTextDisplayed(
                R.string.action_rateable_movie_click_select,
                R.string.action_rateable_movie_click_like,
                R.string.action_rateable_movie_click_rate
        );
    }

    private void checkSeeMoreOptionsMenuWithUndoLikeDisplayed() {
        checkViewsWithTextDisplayed(
                R.string.action_rateable_movie_click_select,
                R.string.action_rateable_movie_click_remove_like,
                R.string.action_rateable_movie_click_rate
        );
    }

    private void checkViewsWithTextDisplayed(int... ids) {
        for (int id : ids) {
            onView(withText(id)).check(matches(isDisplayed()));
        }
    }
}
