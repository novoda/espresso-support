package com.novoda.espresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.novoda.espresso.AccessibilityServiceToggler.Service;

public class AccessibilityServiceTogglingActivity extends Activity {

    public static final String ACTION_DISABLE_ALL_SERVICES = "com.novoda.espresso.DISABLE_ALL_SERVICES";
    public static final String ACTION_SET_SERVICE = "com.novoda.espresso.SET_SERVICE";
    public static final String ENABLED = "enabled";
    public static final String DISABLED = "disabled";

    private AccessibilityServiceToggler serviceToggler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceToggler = AccessibilityServiceToggler.create(getContentResolver());

        Intent intent = getIntent();
        String action = intent.getAction();

        if (ACTION_SET_SERVICE.equalsIgnoreCase(action)) {
            performAction(intent);
        } else if (ACTION_DISABLE_ALL_SERVICES.equalsIgnoreCase(action)) {
            serviceToggler.disableAll();
        }

        finish();
    }

    private void performAction(Intent intent) {
        for (Service service : Service.values()) {
            String value = intent.getStringExtra(service.name());
            if (ENABLED.equalsIgnoreCase(value)) {
                serviceToggler.enable(service);
            } else if (DISABLED.equalsIgnoreCase(value)) {
                serviceToggler.disable(service);
            }
        }
    }
}
