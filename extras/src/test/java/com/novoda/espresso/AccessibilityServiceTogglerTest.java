package com.novoda.espresso;


import com.novoda.accessibility.Service;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(Enclosed.class)
public class AccessibilityServiceTogglerTest {

    private static abstract class Base {

        final AccessibilityServiceToggler.SecureSettings secureSettings = mock(AccessibilityServiceToggler.SecureSettings.class);
        final AccessibilityServiceToggler serviceToggler = new AccessibilityServiceToggler(secureSettings);
    }

    public static class NoParameters extends Base {

        @Test
        public void disableAll() {
            serviceToggler.disableAll();

            verify(secureSettings).enabledAccessibilityServices("");
        }
    }

    @RunWith(Parameterized.class)
    public static class ForEachService extends Base {

        @Parameterized.Parameters
        public static Service[] data() {
            return Service.values();
        }

        private final Service service;

        public ForEachService(Service service) {
            this.service = service;
        }

        @Test
        public void whenCallingEnable_thenAppendsServiceToList() {
            given(secureSettings.enabledAccessibilityServices()).willReturn("foo");

            serviceToggler.enable(service);

            verify(secureSettings).enabledAccessibilityServices(endsWith(":" + service.flattenedComponentName()));
        }

        @Test
        public void givenAlreadyEnabled_whenCallingEnable_thenDoesNotModifyState() {
            given(secureSettings.enabledAccessibilityServices()).willReturn(service.flattenedComponentName());

            serviceToggler.enable(service);

            verify(secureSettings, never()).enabledAccessibilityServices(anyString());
        }

        @Test
        public void givenServiceEnabledLastAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
            given(secureSettings.enabledAccessibilityServices()).willReturn("foo:" + service.flattenedComponentName());

            serviceToggler.disable(service);

            verify(secureSettings).enabledAccessibilityServices("foo");
        }

        @Test
        public void givenServiceEnabledFirstAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
            given(secureSettings.enabledAccessibilityServices()).willReturn(service.flattenedComponentName() + ":foo");

            serviceToggler.disable(service);

            verify(secureSettings).enabledAccessibilityServices("foo");
        }

        @Test
        public void givenServiceEnabledAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
            given(secureSettings.enabledAccessibilityServices()).willReturn("foo:" + service.flattenedComponentName() + ":bar");

            serviceToggler.disable(service);

            verify(secureSettings).enabledAccessibilityServices("foo:bar");
        }

        @Test
        public void givenServiceEnabledIsOnlyOneEnabled_whenCallingDisable_thenSetsEmptyList() {
            given(secureSettings.enabledAccessibilityServices()).willReturn(service.flattenedComponentName());

            serviceToggler.disable(service);

            verify(secureSettings).enabledAccessibilityServices("");
        }
    }
}
