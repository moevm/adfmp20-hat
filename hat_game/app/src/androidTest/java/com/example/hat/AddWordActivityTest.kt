package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class AddWordActivityTest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivity() {
        //Go to AddWord
        Espresso.onView(ViewMatchers.withId(R.id.button_new_game)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_to_players_names))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_to_mode)).perform(ViewActions.click())

        //Add Word: check text
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name4))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name5))
            .check(ViewAssertions.matches(ViewMatchers.withText("Количество слов в словаре: ")))
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name5))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Add Word: enter word
        Espresso.onView(ViewMatchers.withId(R.id.editText_to_add_word))
            .perform(ViewActions.clearText(), ViewActions.typeText("hat"))
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.button_to_add_word)).perform(ViewActions.click())
        Thread.sleep(500)

        //Add Word: click button and go to GAME
        Espresso.onView(ViewMatchers.withId(R.id.button_continue_to_start))
            .check(ViewAssertions.matches(ViewMatchers.withText("Продолжить")))

    }
}