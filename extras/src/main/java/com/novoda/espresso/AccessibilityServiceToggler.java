package com.novoda.espresso;

import android.content.ContentResolver;
import android.provider.Settings;

import com.novoda.accessibility.Service;

class AccessibilityServiceToggler {

    private static final String EMPTY_STRING = "";
    private static final String SERVICES_SEPARATOR = ":";

    private final SecureSettings secureSettings;

    static AccessibilityServiceToggler create(ContentResolver contentResolver) {
        return new AccessibilityServiceToggler(new SecureSettings(contentResolver));
    }

    AccessibilityServiceToggler(SecureSettings secureSettings) {
        this.secureSettings = secureSettings;
    }

    void enable(Service service) {
        String enabledServices = secureSettings.enabledAccessibilityServices();
        if (enabledServices.contains(service.flattenedComponentName())) {
            return;
        }
        secureSettings.enabledAccessibilityServices(enabledServices + SERVICES_SEPARATOR + service.flattenedComponentName());
    }

    void disable(Service service) {
        String enabledServices = secureSettings.enabledAccessibilityServices();
        if (!enabledServices.contains(service.flattenedComponentName())) {
            return;
        }

        String remainingServices = enabledServices
                .replace(SERVICES_SEPARATOR + service.flattenedComponentName(), EMPTY_STRING)
                .replace(service.flattenedComponentName() + SERVICES_SEPARATOR, EMPTY_STRING)
                .replace(service.flattenedComponentName(), EMPTY_STRING);
        secureSettings.enabledAccessibilityServices(remainingServices);
    }

    void disableAll() {
        secureSettings.enabledAccessibilityServices(EMPTY_STRING);
    }

    static class SecureSettings {

        private static final String VALUE_DISABLED = "0";
        private static final String VALUE_ENABLED = "1";

        private final ContentResolver contentResolver;

        SecureSettings(ContentResolver contentResolver) {
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
}
