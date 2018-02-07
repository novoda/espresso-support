package com.novoda.espresso;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;

public class FontScaleRules {

    public static TestRule smallFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.SMALL);
    }

    public static TestRule normalFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.NORMAL);
    }

    public static TestRule largeFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.LARGE);
    }

    public static TestRule hugeFontScaleTestRule() {
        return new FontSizeTestRule(createFontScaleSetting(), FontScale.HUGE);
    }

    private static FontScaleSetting createFontScaleSetting() {
        Context context = InstrumentationRegistry.getTargetContext();
        return new FontScaleSetting(context.getContentResolver(), context.getResources());
    }
}
