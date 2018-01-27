package com.novoda.espresso;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import com.novoda.espresso.core.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ViewTestRule<T extends View> extends ActivityTestRule<EmptyActivity> {

    private final Instrumentation instrumentation;
    private final ViewCreator<T> viewCreator;

    private T view;

    public ViewTestRule(@LayoutRes int layoutId) {
        this(new InflateFromXmlViewCreator<T>(layoutId));
    }

    public ViewTestRule(ViewCreator<T> viewCreator) {
        this(InstrumentationRegistry.getInstrumentation(), viewCreator);
    }

    protected ViewTestRule(Instrumentation instrumentation, ViewCreator<T> viewCreator) {
        super(EmptyActivity.class);
        this.instrumentation = instrumentation;
        this.viewCreator = viewCreator;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        final EmptyActivity activity = getActivity();
        createView(activity);
        runOnMainSynchronously(new Runner<T>() {
            @Override
            public void run(T view) {
                activity.setContentView(view, view.getLayoutParams());
            }
        });
    }

    private void createView(final Activity activity) {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view = viewCreator.createView(activity, (ViewGroup) activity.findViewById(android.R.id.content));
                view.setTag(R.id.espresso_support__view_test_rule, true);
            }
        });
    }

    public void runOnMainSynchronously(final Runner<T> runner) {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                runner.run(view);
            }
        });
    }

    public T getView() {
        return view;
    }

    public interface Runner<T> {

        void run(T view);
    }

    public static Matcher<View> underTest() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                Object tag = item.getTag(R.id.espresso_support__view_test_rule);
                return tag != null && ((Boolean) tag);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
