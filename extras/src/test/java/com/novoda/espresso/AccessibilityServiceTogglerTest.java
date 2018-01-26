package com.novoda.espresso;

import com.novoda.espresso.AccessibilityServiceToggler.Service;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class AccessibilityServiceTogglerTest {

    private final AccessibilityServiceToggler.SecureSettings secureSettings = mock(AccessibilityServiceToggler.SecureSettings.class);
    private final AccessibilityServiceToggler serviceToggler = new AccessibilityServiceToggler(secureSettings);

    @Test
    public void whenCallingEnable_thenAppendsServiceToList() {
        for (Service service : Service.values()) {
            whenCallingEnable_thenAppendsServiceToList(service);
            reset(secureSettings);
        }
    }

    private void whenCallingEnable_thenAppendsServiceToList(Service service) {
        given(secureSettings.enabledAccessibilityServices()).willReturn("foo");

        serviceToggler.enable(service);

        verify(secureSettings).enabledAccessibilityServices(endsWith(":" + service.qualifiedName()));
    }

    @Test
    public void givenAlreadyEnabled_whenCallingEnable_thenDoesNotModifyState() {
        for (Service service : Service.values()) {
            givenAlreadyEnabled_whenCallingEnable_thenDoesNotModifyState(service);
        }
    }

    private void givenAlreadyEnabled_whenCallingEnable_thenDoesNotModifyState(Service service) {
        given(secureSettings.enabledAccessibilityServices()).willReturn(service.qualifiedName());

        serviceToggler.enable(service);

        verify(secureSettings, never()).enabledAccessibilityServices(anyString());
    }

    @Test
    public void givenServiceEnabledLastAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
        for (Service service : Service.values()) {
            givenServiceEnabledLastAmongOthers_whenCallingDisable_thenRemovesServiceFromList(service);
            reset(secureSettings);
        }
    }

    private void givenServiceEnabledLastAmongOthers_whenCallingDisable_thenRemovesServiceFromList(Service service) {
        given(secureSettings.enabledAccessibilityServices()).willReturn("foo:" + service.qualifiedName());

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("foo");
    }

    @Test
    public void givenServiceEnabledFirstAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
        for (Service service : Service.values()) {
            givenServiceEnabledFirstAmongOthers_whenCallingDisable_thenRemovesServiceFromList(service);
            reset(secureSettings);
        }
    }

    private void givenServiceEnabledFirstAmongOthers_whenCallingDisable_thenRemovesServiceFromList(Service service) {
        given(secureSettings.enabledAccessibilityServices()).willReturn(service.qualifiedName() + ":foo");

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("foo");
    }

    @Test
    public void givenServiceEnabledAmongOthers_whenCallingDisable_thenRemovesServiceFromList() {
        for (Service service : Service.values()) {
            givenServiceEnabledAmongOthers_whenCallingDisable_thenRemovesServiceFromList(service);
            reset(secureSettings);
        }
    }

    private void givenServiceEnabledAmongOthers_whenCallingDisable_thenRemovesServiceFromList(Service service) {
        given(secureSettings.enabledAccessibilityServices()).willReturn("foo:" + service.qualifiedName() + ":bar");

        serviceToggler.disable(service);

        verify(secureSettings).enabledAccessibilityServices("foo:bar");
    }

    @Test
    public void givenServiceEnabledIsOnlyOneEnabled_whenCallingDisable_thenSetsEmptyList() {
        for (Service service : Service.values()) {
            givenServiceEnabledIsOnlyOneEnabled_whenCallingDisable_thenSetsEmptyList(service);
            reset(secureSettings);
        }
    }

    private void givenServiceEnabledIsOnlyOneEnabled_whenCallingDisable_thenSetsEmptyList(Service service) {
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
