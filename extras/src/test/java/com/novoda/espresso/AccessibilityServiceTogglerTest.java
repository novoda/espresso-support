package com.novoda.espresso;

import com.novoda.espresso.AccessibilityServiceToggler.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(Parameterized.class)
public class AccessibilityServiceTogglerTest {

    @Parameterized.Parameters
    public static Service[] data() {
        return Service.values();
    }

    private final AccessibilityServiceToggler.SecureSettings secureSettings = mock(AccessibilityServiceToggler.SecureSettings.class);
    private final AccessibilityServiceToggler serviceToggler = new AccessibilityServiceToggler(secureSettings);
    private final Service service;

    public AccessibilityServiceTogglerTest(Service service) {
        this.service = service;
    }

    @Test
    public void whenCallingEnable_thenAppendsServiceToList() {
        given(secureSettings.enabledAccessibilityServices()).willReturn("foo");

        serviceToggler.enable(service);

        verify(secureSettings).enabledAccessibilityServices(endsWith(":" + service.qualifiedName()));
    }

    @Test
    public void givenAlreadyEnabled_whenCallingEnable_thenDoesNotModifyState() {
        given(secureSettings.enabledAccessibilityServices()).willReturn(service.qualifiedName());

        serviceToggler.enable(service);

        verify(secureSettings, never()).enabledAccessibilityServices(anyString());
    }

    @Test
    public void givenServiceEnabledLastAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
        given(secureSettings.enabledAccessibilityServices()).willReturn("foo:" + service.qualifiedName());

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("foo");
    }

    @Test
    public void givenServiceEnabledFirstAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
        given(secureSettings.enabledAccessibilityServices()).willReturn(service.qualifiedName() + ":foo");

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("foo");
    }

    @Test
    public void givenServiceEnabledAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
        given(secureSettings.enabledAccessibilityServices()).willReturn("foo:" + service.qualifiedName() + ":bar");

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("foo:bar");
    }

    @Test
    public void givenServiceEnabledIsOnlyOneEnabled_whenCallingDisable_thenSetsEmptyList() {
        given(secureSettings.enabledAccessibilityServices()).willReturn(service.qualifiedName());

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("");
    }

    @Test
    public void disableAll() {
        serviceToggler.disableAll();

        verify(secureSettings).enabledAccessibilityServices("");
    }
}
