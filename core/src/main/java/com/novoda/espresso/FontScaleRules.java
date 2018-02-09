package com.novoda.espresso;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;

public class FontScaleRules {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static TestRule smallFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.SMALL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static TestRule normalFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.NORMAL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static TestRule largeFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.LARGE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static TestRule hugeFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.HUGE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static FontScaleSetting createFontScaleSetting() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return new FontScaleSetting(instrumentation.getUiAutomation(), instrumentation.getTargetContext().getResources());
    }
}
