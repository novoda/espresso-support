package com.novoda.espresso;

import android.app.Instrumentation;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.view.View;

public class TalkBackViewTestRule<T extends View> extends ViewTestRule<T> {

    private final TalkBackStateSettingRequester talkBackStateSettingRequester = new TalkBackStateSettingRequester();

    public TalkBackViewTestRule(@LayoutRes int layoutId) {
        this(new InflateFromXmlViewCreator<T>(layoutId));
    }

    public TalkBackViewTestRule(ViewCreator<T> viewCreator) {
        this(InstrumentationRegistry.getInstrumentation(), viewCreator);
    }

    protected TalkBackViewTestRule(Instrumentation instrumentation, ViewCreator<T> viewCreator) {
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
