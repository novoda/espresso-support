package com.novoda.espresso;

import android.annotation.TargetApi;
import android.app.UiAutomation;
import android.content.res.Resources;
import android.os.Build;

class FontScaleSetting {

    private final UiAutomation uiAutomation;
    private final Resources resources;

    FontScaleSetting(UiAutomation uiAutomation, Resources resources) {
        this.uiAutomation = uiAutomation;
        this.resources = resources;
    }

    FontScale get() {
        return FontScale.from(resources.getConfiguration().fontScale);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void set(FontScale scale) {
        uiAutomation.executeShellCommand("settings put system font_scale " + scale.value());
    }
}
