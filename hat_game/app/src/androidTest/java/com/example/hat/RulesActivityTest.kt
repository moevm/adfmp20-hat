package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class RulesActivityTest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivity() {
        //Main Activity: go to Rules
        Espresso.onView(ViewMatchers.withId(R.id.button_rules)).perform(ViewActions.click())

        //Rules: check text
        Espresso.onView(ViewMatchers.withId(R.id.textViewRules))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}