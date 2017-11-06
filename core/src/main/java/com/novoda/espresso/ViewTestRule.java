package com.novoda.espresso;

import android.app.Instrumentation;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

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

    public ViewTestRule(Instrumentation instrumentation, ViewCreator<T> viewCreator) {
        super(EmptyActivity.class);
        this.instrumentation = instrumentation;
        this.viewCreator = viewCreator;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        final EmptyActivity activity = getActivity();
        view = viewCreator.createView(activity, (ViewGroup) activity.findViewById(android.R.id.content));

        runOnMainSynchronously(new Runner<T>() {
            @Override
            public void run(T view) {
                ViewGroup.LayoutParams matchParent = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                activity.setContentView(view, matchParent);
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
}
