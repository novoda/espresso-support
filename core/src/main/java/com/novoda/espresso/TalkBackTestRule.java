package com.novoda.espresso;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.view.accessibility.AccessibilityManager;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_SPOKEN;

public class TalkBackTestRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new TalkBackStatement(base);
    }

    private static class TalkBackStatement extends Statement {

        private static final int SLEEP_DELAY_MILLIS = 10;

        private final Statement baseStatement;
        private final TalkBackStateSettingRequester talkBackStateSettingRequester = new TalkBackStateSettingRequester(0);
        private final AccessibilityManager a11yManager = (AccessibilityManager) InstrumentationRegistry.getInstrumentation().getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

        TalkBackStatement(Statement baseStatement) {
            this.baseStatement = baseStatement;
        }

        @Override
        public void evaluate() throws Throwable {
            talkBackStateSettingRequester.requestEnableTalkBack();
            sleepUntilTalkBackIsEnabled();

            baseStatement.evaluate();

            talkBackStateSettingRequester.requestDisableTalkBack();
            sleepUntilTalkBackIsDisabled();
        }

        private void sleepUntilTalkBackIsEnabled() {
            while (talkBackDisabled()) {
                SystemClock.sleep(SLEEP_DELAY_MILLIS);
            }
        }

        private void sleepUntilTalkBackIsDisabled() {
            while (talkBackEnabled()) {
                SystemClock.sleep(SLEEP_DELAY_MILLIS);
            }
        }

        private boolean talkBackEnabled() {
            return !talkBackDisabled();
        }

        private boolean talkBackDisabled() {
            return a11yManager.getEnabledAccessibilityServiceList(FEEDBACK_SPOKEN).isEmpty();
        }
    }
}
