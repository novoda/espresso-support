package com.novoda.espresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TalkBackStateSettingActivity extends Activity {

    public static final String ACTION_ENABLE_TALKBACK = "com.novoda.espresso.ENABLE_TALKBACK";
    public static final String ACTION_DISABLE_TALKBACK = "com.novoda.espresso.DISABLE_TALKBACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null || intent.getAction() == null) {
            finish();
            return;
        }
        switch (intent.getAction()) {
            case ACTION_ENABLE_TALKBACK:
                AccessibilityServiceToggler.create(getContentResolver()).enableTalkBack();
                break;
            case ACTION_DISABLE_TALKBACK:
                AccessibilityServiceToggler.create(getContentResolver()).disableTalkBack();
                break;
        }
        finish();
    }
}
