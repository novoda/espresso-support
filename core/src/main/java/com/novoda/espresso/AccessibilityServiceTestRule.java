package com.novoda.espresso;

import android.os.SystemClock;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

class AccessibilityServiceTestRule implements TestRule {

    private final Helper helper;

    AccessibilityServiceTestRule(Helper helper) {
        this.helper = helper;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new AccessibilityServiceStatement(base, helper);
    }

    private static class AccessibilityServiceStatement extends Statement {

        private static final int SLEEP_TO_WAIT_FOR_TOGGLE_MILLIS = 100;
        private static final int MAX_RETRIES_TO_WAIT_FOR_TOGGLE = 15;

        private final Statement baseStatement;
        private final Helper helper;

        AccessibilityServiceStatement(Statement baseStatement, Helper helper) {
            this.baseStatement = baseStatement;
            this.helper = helper;
        }

        @Override
        public void evaluate() throws Throwable {
            helper.requestEnableService();
            sleepUntil(serviceIsEnabled());

            baseStatement.evaluate();

            helper.requestDisableService();
            sleepUntil(serviceIsDisabled());
        }

        private void sleepUntil(Condition condition) {
            int retries = 0;
            while (!condition.holds()) {
                SystemClock.sleep(SLEEP_TO_WAIT_FOR_TOGGLE_MILLIS);
                if (retries == MAX_RETRIES_TO_WAIT_FOR_TOGGLE) {
                    throw serviceToggleTimeOutError();
                }
                retries++;
            }
        }

        private Condition serviceIsEnabled() {
            return new Condition() {
                @Override
                public boolean holds() {
                    return helper.reportsEnabled();
                }
            };
        }

        private Condition serviceIsDisabled() {
            return new Condition() {
                @Override
                public boolean holds() {
                    return !helper.reportsEnabled();
                }
            };
        }

        private AssertionError serviceToggleTimeOutError() {
            return new AssertionError("Spent too long waiting for " + helper.serviceName() + " to toggle.");
        }
    }

    interface Helper {

        String serviceName();

        void requestEnableService();

        void requestDisableService();

        boolean reportsEnabled();
    }
}
