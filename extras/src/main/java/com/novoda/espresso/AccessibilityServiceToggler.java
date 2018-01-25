package com.novoda.espresso;

import android.content.ContentResolver;
import android.provider.Settings;

class AccessibilityServiceToggler {

    private static final String TALKBACK_SERVICE_NAME = "com.google.android.marvin.talkback/.TalkBackService";
    private static final String EMPTY_STRING = "";

    private final SecureSettings secureSettings;

    static AccessibilityServiceToggler create(ContentResolver contentResolver) {
        return new AccessibilityServiceToggler(new SecureSettings(contentResolver));
    }

    private AccessibilityServiceToggler(SecureSettings secureSettings) {
        this.secureSettings = secureSettings;
    }

    void enable(Services services) {
        String enabledServices = secureSettings.enabledAccessibilityServices();
        if (enabledServices.contains(TALKBACK_SERVICE_NAME)) {
            return;
        }
        secureSettings.enabledAccessibilityServices(enabledServices + (":" + services.serviceName));
    }

    void disable(Services services) {
        String enabledServices = secureSettings.enabledAccessibilityServices();
        if (!enabledServices.contains(services.serviceName)) {
            return;
        }

        // TODO: regex this up
        String remainingServices = enabledServices
                .replace(":" + services.serviceName, EMPTY_STRING)
                .replace(services.serviceName + ":", EMPTY_STRING)
                .replace(services.serviceName, EMPTY_STRING);
        secureSettings.enabledAccessibilityServices(remainingServices);
    }

    private static class SecureSettings {

        private static final String VALUE_DISABLED = "0";
        private static final String VALUE_ENABLED = "1";

        private final ContentResolver contentResolver;

        private SecureSettings(ContentResolver contentResolver) {
            this.contentResolver = contentResolver;
        }

        String enabledAccessibilityServices() {
            return Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        }

        void enabledAccessibilityServices(String services) {
            Settings.Secure.putString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, services);
            accessibilityEnabled(services.isEmpty() ? VALUE_DISABLED : VALUE_ENABLED);
        }

        private void accessibilityEnabled(String enabled) {
            Settings.Secure.putString(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED, enabled);
        }
    }

    enum Services {

        TALKBACK("com.google.android.marvin.talkback/.TalkBackService");

        private final String serviceName;

        Services(String serviceName) {
            this.serviceName = serviceName;
        }
    }
}
