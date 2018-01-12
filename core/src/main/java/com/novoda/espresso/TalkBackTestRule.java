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

        private static final int SLEEP_TO_WAIT_FOR_TALK_BACK_MILLIS = 100;
        private static final int MAX_RETRIES_TO_WAIT_FOR_TALK_BACK = 15;

        private final Statement baseStatement;
        private final TalkBackStateSettingRequester talkBackStateSettingRequester = new TalkBackStateSettingRequester(0);
        private final AccessibilityManager a11yManager = (AccessibilityManager) InstrumentationRegistry.getInstrumentation().getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

        TalkBackStatement(Statement baseStatement) {
            this.baseStatement = baseStatement;
        }

        @Override
        public void evaluate() throws Throwable {
            talkBackStateSettingRequester.requestEnableTalkBack();
            sleepUntil(talkBackIsEnabled());

            baseStatement.evaluate();

            talkBackStateSettingRequester.requestDisableTalkBack();
            sleepUntil(talkBackIsDisabled());
        }

        private void sleepUntil(Condition condition) {
            int retries = 0;
            while (!condition.holds()) {
                SystemClock.sleep(SLEEP_TO_WAIT_FOR_TALK_BACK_MILLIS);
                if (retries == MAX_RETRIES_TO_WAIT_FOR_TALK_BACK) {
                    throw talkBackToggleTimeOutError();
                }
                retries++;
            }
        }

        private Condition talkBackIsEnabled() {
            return new Condition() {
                @Override
                public boolean holds() {
                    return spokenFeedbackServiceRunning();
                }
            };
        }

        private Condition talkBackIsDisabled() {
            return new Condition() {
                @Override
                public boolean holds() {
                    return !spokenFeedbackServiceRunning();
                }
            };
        }

        private boolean spokenFeedbackServiceRunning() {
            return !a11yManager.getEnabledAccessibilityServiceList(FEEDBACK_SPOKEN).isEmpty();
        }

        private AssertionError talkBackToggleTimeOutError() {
            return new AssertionError("Spent too long waiting for TalkBack to toggle.");
        }

        private interface Condition {

            boolean holds();
        }
    }
}
