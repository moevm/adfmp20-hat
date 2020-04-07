package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test

class SettingsActivityTest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivity() {
        //Main Activity: click button and go to Settings
        Espresso.onView(ViewMatchers.withId(R.id.button_new_game)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textView_settings))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Settings: check text
        Espresso.onView(ViewMatchers.withId(R.id.textView_settings))
            .check(ViewAssertions.matches(ViewMatchers.withText("Настройки")))
        Espresso.onView(ViewMatchers.withId(R.id.textView_settings))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Settings: check spinner for time
        Espresso.onView(ViewMatchers.withId(R.id.textView2))
            .check(ViewAssertions.matches(ViewMatchers.withText("Количество секунд на ход:")))
        Espresso.onView(ViewMatchers.withId(R.id.textView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.spinner_time_for_step))
            .perform(ViewActions.click())
        Espresso.onData(CoreMatchers.anything()).atPosition(0).perform(ViewActions.click())

        //Settings: check spinner for number of words
        Espresso.onView(ViewMatchers.withId(R.id.textView_words_in_vocabular))
            .check(ViewAssertions.matches(ViewMatchers.withText("Количество слов в шляпе:")))
        Espresso.onView(ViewMatchers.withId(R.id.textView_words_in_vocabular))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.spinner_words_in_vocabular))
            .perform(ViewActions.click())
        Espresso.onData(CoreMatchers.anything()).atPosition(0).perform(ViewActions.click())

        //Settings: check modes
        Espresso.onView(ViewMatchers.withId(R.id.textView5))
            .check(ViewAssertions.matches(ViewMatchers.withText("Уровень сложности:")))
        Espresso.onView(ViewMatchers.withId(R.id.textView5))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.checkBoxEasy))
            .check(ViewAssertions.matches(ViewMatchers.withText("Легкий")))
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxEasy))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.checkBoxNorm))
            .check(ViewAssertions.matches(ViewMatchers.withText("Средний")))
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxNorm))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxNorm)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.checkBoxHard))
            .check(ViewAssertions.matches(ViewMatchers.withText("Сложный")))
        Espresso.onView(ViewMatchers.withId(R.id.checkBoxHard))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Settings: click button and go to PlayersNames
        Espresso.onView(ViewMatchers.withId(R.id.button_to_players_names))
            .check(ViewAssertions.matches(ViewMatchers.withText("Продолжить")))
    }
}