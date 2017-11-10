package com.novoda.movies;

import android.content.Context;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.espresso.ViewCreator;
import com.novoda.espresso.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProgrammaticallyCreatedViewTest {

    private static final String TEXT = "Hello world!";

    @Rule
    public ViewTestRule<TextView> viewTestRule = new ViewTestRule<>(new TextViewCreator());

    @Test
    public void createdViewIsDisplayed() {
        onView(withText(TEXT)).check(matches(isDisplayed()));
    }

    private static class TextViewCreator implements ViewCreator<TextView> {

        @Override
        public TextView createView(Context context, ViewGroup parentView) {
            TextView textView = new TextView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);
            textView.setText(TEXT);
            return textView;
        }
    }
}
