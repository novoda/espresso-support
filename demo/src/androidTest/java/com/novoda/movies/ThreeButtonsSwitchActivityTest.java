package com.novoda.movies;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.novoda.espresso.SwitchAccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ThreeButtonsSwitchActivityTest {

    @Rule
    public ActivityTestRule<ThreeButtonsActivity> activityRule = new ActivityTestRule<>(ThreeButtonsActivity.class);

    private final SwitchAccess switchAccess = new SwitchAccess();

    @Test
    public void initiallySaysNothingYet() {
        onView(withId(R.id.reaction_view)).check(matches(withText("nothing yet")));
    }

    @Test
    public void fooo() {
        switchAccess.select();
        onView(withId(R.id.reaction_view)).check(matches(withText("clicked a")));
    }
}
