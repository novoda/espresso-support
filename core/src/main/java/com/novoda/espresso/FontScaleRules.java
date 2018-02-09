package com.novoda.espresso;

import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.os.Build;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class FontScaleRules {

    public static TestRule smallFontScaleTestRule() {
        return new FontScaleTestRule(createFontScaleSetting(), FontScale.SMALL);
    }

    public static TestRule normalFontScaleTestRule() {
        return new FontScaleTestRule(createFontScaleSetting(), FontScale.NORMAL);
    }

    public static TestRule largeFontScaleTestRule() {
        return new FontScaleTestRule(createFontScaleSetting(), FontScale.LARGE);
    }

    public static TestRule hugeFontScaleTestRule() {
        return new FontScaleTestRule(createFontScaleSetting(), FontScale.HUGE);
    }

    private static FontScaleSetting createFontScaleSetting() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return new FontScaleSetting(instrumentation.getUiAutomation(), instrumentation.getTargetContext().getResources());
    }
}
