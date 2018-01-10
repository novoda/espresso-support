package com.novoda.espresso;

import android.os.SystemClock;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TalkBackTestRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new TalkBackStatement(base);
    }

    private static class TalkBackStatement extends Statement {

        private static final int DEFAULT_DELAY_MILLIS = 1500;

        private final TalkBackStateSettingRequester talkBackStateSettingRequester = new TalkBackStateSettingRequester(0);
        private final Statement baseStatement;

        TalkBackStatement(Statement baseStatement) {
            this.baseStatement = baseStatement;
        }

        @Override
        public void evaluate() throws Throwable {
            talkBackStateSettingRequester.requestEnableTalkBack();
            sleepToAllowTalkBackServiceToChangeState();

            baseStatement.evaluate();

            talkBackStateSettingRequester.requestDisableTalkBack();
            sleepToAllowTalkBackServiceToChangeState();
        }

        private void sleepToAllowTalkBackServiceToChangeState() {
            SystemClock.sleep(DEFAULT_DELAY_MILLIS);
        }
    }
}
