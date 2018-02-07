package com.novoda.espresso;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.provider.Settings;

class FontScaleSetting {

    private final ContentResolver contentResolver;
    private final Resources resources;



    FontScaleSetting(ContentResolver contentResolver, Resources resources) {
        this.contentResolver = contentResolver;
        this.resources = resources;
    }

    FontScale get() {
        return FontScale.from(resources.getConfiguration().fontScale);
    }

    /**
     * Prior to M, devices need to be rebooted in order to reload the configuration, making it
     * useless for Espresso testing rules.
     */
    void set(FontScale scale) {
        Settings.System.putFloat(contentResolver, Settings.System.FONT_SCALE, scale.value());
    }
}
