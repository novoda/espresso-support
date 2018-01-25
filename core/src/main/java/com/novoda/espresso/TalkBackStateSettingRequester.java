package com.novoda.espresso;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;

import static com.novoda.espresso.AccessibilityServiceTogglingActivity.ACTION_DISABLE_TALKBACK;
import static com.novoda.espresso.AccessibilityServiceTogglingActivity.ACTION_ENABLE_TALKBACK;

/**
 * Send intents to AccessibilityServiceTogglingActivity to start and stop TalkBack.
 * <p>
 * By default, includes a delay after sending the intent to allow the state to change. This is customizable
 * via the constructor {@link TalkBackStateSettingRequester#TalkBackStateSettingRequester(long, Context)}.
 */
public class TalkBackStateSettingRequester {

    private final long delayMillis;
    private final Context context;

    public TalkBackStateSettingRequester() {
        this(0);
    }

    /**
     * @param delayMillis time to sleep after toggling TalkBack state
     */
    public TalkBackStateSettingRequester(long delayMillis) {
        this(delayMillis, InstrumentationRegistry.getTargetContext());
    }

    /**
     * @param delayMillis time to sleep after toggling TalkBack state
     * @param context
     */
    public TalkBackStateSettingRequester(long delayMillis, Context context) {
        this.delayMillis = delayMillis;
        this.context = context;
    }

    /**
     * Send intent to start TalkBack.
     */
    public void requestEnableTalkBack() {
        sendIntent(ACTION_ENABLE_TALKBACK);
    }

    /**
     * Send intent to stop TalkBack.
     */
    public void requestDisableTalkBack() {
        sendIntent(ACTION_DISABLE_TALKBACK);
    }

    private void sendIntent(String action) {
        Intent intent = new Intent(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        sleepToAllowTalkBackServiceToChangeState();
    }

    private void sleepToAllowTalkBackServiceToChangeState() {
        SystemClock.sleep(delayMillis);
    }

}
