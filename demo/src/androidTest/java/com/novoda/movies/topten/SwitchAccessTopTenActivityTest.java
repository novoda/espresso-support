package com.novoda.movies.topten;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.novoda.espresso.AccessibilityRules;

import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SwitchAccessTopTenActivityTest {

    private final ActivityTestRule activityTestRule = new ActivityTestRule<>(TopTenActivity.class);

    @Rule
    public RuleChain ruleChain = RuleChain.outerRule(AccessibilityRules.createSwitchAccessTestRule()).around(activityTestRule);


}
