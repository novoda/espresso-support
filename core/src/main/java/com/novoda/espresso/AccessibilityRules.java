package com.novoda.espresso;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.view.accessibility.AccessibilityManager;

import org.junit.rules.TestRule;

import java.util.List;

import static com.novoda.espresso.AccessibilityServiceToggler.Service;

public class AccessibilityRules {

    public static TestRule createTalkBackTestRule() {
        return new AccessibilityServiceTestRule(createHelper(Service.TALKBACK));
    }

    public static TestRule createSwitchAccessTestRule() {
        return new AccessibilityServiceTestRule(createHelper(Service.SWITCH_ACCESS));
    }

    public static TestRule createSelectToSpeakTestRule() {
        return new AccessibilityServiceTestRule(createHelper(Service.SELECT_TO_SPEAK));
    }

    private static AccessibilityServiceTestRule.Helper createHelper(final Service service) {
        final Context context = InstrumentationRegistry.getTargetContext();
        final AccessibilityManager a11yManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);

        return new AccessibilityServiceTestRule.Helper() {
            @Override
            public String serviceName() {
                return service.name();
            }

            @Override
            public void requestEnableService() {
                requestState(AccessibilityServiceTogglingActivity.ENABLED);
            }

            @Override
            public void requestDisableService() {
                requestState(AccessibilityServiceTogglingActivity.DISABLED);
            }

            private void requestState(String value) {
                Intent intent = new Intent(AccessibilityServiceTogglingActivity.ACTION_SET_SERVICE);
                intent.putExtra(service.name(), value);
                context.startActivity(intent);
            }

            @Override
            public boolean reportsEnabled() {
                List<AccessibilityServiceInfo> enabledAccessibilityServiceList = a11yManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
                for (AccessibilityServiceInfo accessibilityServiceInfo : enabledAccessibilityServiceList) {
                    if (accessibilityServiceInfo.getId().equals(service.qualifiedName())) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
