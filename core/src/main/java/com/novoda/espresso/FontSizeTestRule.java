package com.novoda.espresso;

import android.os.SystemClock;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

class FontSizeTestRule implements TestRule {

    private final FontScaleSetting fontScaleSetting;
    private final FontScale fontScale;

    FontSizeTestRule(FontScaleSetting fontScaleSetting, FontScale fontScale) {
        this.fontScaleSetting = fontScaleSetting;
        this.fontScale = fontScale;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new FontSizeStatement(base, fontScaleSetting, fontScale);
    }

    private static class FontSizeStatement extends Statement {

        private static final int SLEEP_TO_WAIT_FOR_SETTING_MILLIS = 100;
        private static final int MAX_RETRIES_TO_WAIT_FOR_SETTING = 15;

        private final Statement baseStatement;
        private final FontScaleSetting scaleSetting;
        private final FontScale scale;

        FontSizeStatement(Statement baseStatement, FontScaleSetting scaleSetting, FontScale scale) {
            this.baseStatement = baseStatement;
            this.scaleSetting = scaleSetting;
            this.scale = scale;
        }

        @Override
        public void evaluate() throws Throwable {
            FontScale initialScale = scaleSetting.get();
            scaleSetting.set(scale);
            sleepUntil(scaleMatches(scale));

            baseStatement.evaluate();

            scaleSetting.set(initialScale);
            sleepUntil(scaleMatches(initialScale));
        }

        private Condition scaleMatches(final FontScale scale) {
            return new Condition() {
                @Override
                public boolean holds() {
                    return scaleSetting.get() == scale;
                }
            };
        }

        private void sleepUntil(Condition condition) {
            int retries = 0;
            while (!condition.holds()) {
                SystemClock.sleep(SLEEP_TO_WAIT_FOR_SETTING_MILLIS);
                if (retries == MAX_RETRIES_TO_WAIT_FOR_SETTING) {
                    throw timeoutError();
                }
                retries++;
            }
        }

        private AssertionError timeoutError() {
            return new AssertionError("Spent too long waiting trying to set scale.");
        }
    }
}
