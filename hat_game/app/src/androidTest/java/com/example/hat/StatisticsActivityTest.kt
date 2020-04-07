package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class StatisticsActivityTest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivity() {
        //Main Activity: go to Statistics
        Espresso.onView(ViewMatchers.withId(R.id.button_to_statistics)).perform(ViewActions.click())

        //Statistics: check text
        Espresso.onView(ViewMatchers.withId(R.id.textView_stat))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)

        //Go to Main Activity
        Espresso.onView(ViewMatchers.withId(R.id.button_go_to_main)).perform(ViewActions.click())
    }
}