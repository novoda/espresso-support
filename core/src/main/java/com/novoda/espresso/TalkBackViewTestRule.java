package com.novoda.espresso;

import android.app.Instrumentation;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.view.LayoutInflater;
import android.view.View;

public class TalkBackViewTestRule<T extends View> extends ViewTestRule<T> {

    private final TalkBackStateSettingRequester talkBackStateSettingRequester = new TalkBackStateSettingRequester();

    public static <T extends View> ViewTestRule<T> withViewFromXml(@LayoutRes int layoutId) {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        LayoutInflater layoutInflater = LayoutInflater.from(instrumentation.getTargetContext());
        ViewCreator<T> viewCreator = new InflateFromXmlViewCreator<>(layoutId, layoutInflater);
        return new TalkBackViewTestRule<>(instrumentation, viewCreator);
    }

    public TalkBackViewTestRule(Instrumentation instrumentation, ViewCreator<T> viewCreator) {
        super(instrumentation, viewCreator);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        talkBackStateSettingRequester.requestEnableTalkBack();
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        talkBackStateSettingRequester.requestDisableTalkBack();
    }
}
