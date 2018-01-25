package com.novoda.espresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.novoda.espresso.AccessibilityServiceToggler.Service;

public class AccessibilityServiceTogglingActivity extends Activity {

    public static final String ACTION_ENABLE_TALKBACK = "com.novoda.espresso.ENABLE_TALKBACK";
    public static final String ACTION_DISABLE_TALKBACK = "com.novoda.espresso.DISABLE_TALKBACK";
    public static final String ACTION_ENABLE_SWITCH_ACCESS= "com.novoda.espresso.ENABLE_SWITCH_ACCESS";
    public static final String ACTION_DISABLE_SWITCH_ACCESS = "com.novoda.espresso.DISABLE_SWITCH_ACCESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null || intent.getAction() == null) {
            finish();
            return;
        }

        AccessibilityServiceToggler serviceToggler = AccessibilityServiceToggler.create(getContentResolver());
        switch (intent.getAction()) {
            case ACTION_ENABLE_TALKBACK:
                serviceToggler.enable(Service.TALKBACK);
                break;
            case ACTION_DISABLE_TALKBACK:
                serviceToggler.disable(Service.TALKBACK);
                break;
            case ACTION_ENABLE_SWITCH_ACCESS:
                serviceToggler.enable(Service.SWITCH_ACCESS);
                break;
            case ACTION_DISABLE_SWITCH_ACCESS:
                serviceToggler.disable(Service.SWITCH_ACCESS);
                break;
        }
        finish();
    }
}
