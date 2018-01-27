package com.novoda.espresso;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.view.KeyEvent;

/**
 * It's necessary to pre-configure the Switch Access service on the test device to set-up with:
 *
 * - a two-button switch
 * - select mapped to the enter key
 * - next mapped to the tab key
 */
public class SwitchAccess {

    private final UiDevice uiDevice;

    public SwitchAccess() {
        this(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()));
    }

    public SwitchAccess(UiDevice uiDevice) {
        this.uiDevice = uiDevice;
    }

    public void select() {
        uiDevice.pressEnter();
    }

    public void next() {
        uiDevice.pressKeyCode(KeyEvent.KEYCODE_TAB);
    }
}