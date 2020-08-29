package com.novoda.movies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.novoda.espresso.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProgrammaticallyCreatedViewTest {

    private static final String TEXT = "Hello world!";

    @Rule
    public ViewTestRule<TextView> viewTestRule = new ViewTestRule<>(new TestTextView(
            InstrumentationRegistry.getTargetContext()));

    @Test
    public void createdViewIsDisplayed() {
        onView(withText(TEXT)).check(matches(isDisplayed()));
    }

    private static class TestTextView extends TextView {
        private TestTextView(final Context context) {
            super(context);
            setText(TEXT);
        }
    }
}
