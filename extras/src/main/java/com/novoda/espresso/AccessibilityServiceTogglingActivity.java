package com.novoda.espresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.novoda.espresso.AccessibilityServiceToggler.Service;

import java.util.Locale;

public class AccessibilityServiceTogglingActivity extends Activity {

    private static final String ACTION_SET = "com.novoda.espresso.SET_SERVICE";
    private static final String VALUE_ENABLED = "enabled";
    private static final String VALUE_DISABLED = "disabled";

    private AccessibilityServiceToggler serviceToggler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceToggler = AccessibilityServiceToggler.create(getContentResolver());

        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(ACTION_SET)) {
            performAction(intent);
        }

        finish();
    }

    private void performAction(Intent intent) {
        for (Service service : Service.values()) {
            String value = intent.getStringExtra(service.name().toLowerCase(Locale.US));
            if (VALUE_ENABLED.equalsIgnoreCase(value)) {
                serviceToggler.enable(service);
            } else if (VALUE_DISABLED.equalsIgnoreCase(value)) {
                serviceToggler.disable(service);
            }
        }
    }
}
