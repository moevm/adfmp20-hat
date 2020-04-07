package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testGame() {
        //Main Activity: check button_to_statistics
        Espresso.onView(ViewMatchers.withId(R.id.button_to_statistics))
            .check(ViewAssertions.matches(ViewMatchers.withText("Статистика")))
        Espresso.onView(ViewMatchers.withId(R.id.button_to_statistics))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Main Activity: check button_rules
        Espresso.onView(ViewMatchers.withId(R.id.button_rules))
            .check(ViewAssertions.matches(ViewMatchers.withText("Правила")))
        Espresso.onView(ViewMatchers.withId(R.id.button_rules))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Main Activity: check button_new_game
        Espresso.onView(ViewMatchers.withId(R.id.button_new_game))
            .check(ViewAssertions.matches(ViewMatchers.withText("Новая игра")))
        Espresso.onView(ViewMatchers.withId(R.id.button_new_game))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}