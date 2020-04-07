package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test


class UITest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testGame() {
        val team1 = "Julia"
        val team2 = "Sergey"

        //Main Activity: click button and go to Settings
        onView(withId(R.id.button_new_game)).perform(click())
        onView(withId(R.id.spinner_time_for_step)).perform(click())
        onData(anything()).atPosition(0).perform(click())
        onView(withId(R.id.spinner_words_in_vocabular)).perform(click())
        onData(anything()).atPosition(0).perform(click())

        //Settings: click button and go to PlayersNames
        onView(withId(R.id.button_to_players_names)).perform(click())

        //PlayersNames: enter team1 name
        onView(withId(R.id.textView_player_name)).perform(clearText(), typeText(team1))
        Thread.sleep(500)

        //PlayersNames: enter team2 name
        onView(withId(R.id.textView_player_name2)).perform(clearText(), typeText(team2))
        Thread.sleep(1000)

        Espresso.pressBack()
        //PlayersNames: click button and go to Add Word
        onView(withId(R.id.button_to_mode)).perform(click())

        //Add Word: enter word
        onView(withId(R.id.editText_to_add_word)).perform(clearText(), typeText("hat"))
        Thread.sleep(500)
        onView(withId(R.id.button_to_add_word)).perform(click())
        Thread.sleep(500)

        //Add Word: click button and go to GAME
        Espresso.pressBack()
        onView(withId(R.id.button_continue_to_start)).perform(click())

        //Game: check text
        onView(withId(R.id.textViewComand)).check(matches(withText("Угадывает: ")))
        onView(withId(R.id.textViewComand)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewComName)).check(matches(isDisplayed()))

        onView(withId(R.id.textViewNum)).check(matches(withText("Слов в шляпе:  ")))
        onView(withId(R.id.textViewNum)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewNumWords)).check(matches(isDisplayed()))

        onView(withId(R.id.textViewNameScore)).check(matches(withText("Счет: ")))
        onView(withId(R.id.textViewNameScore)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewScore)).check(matches(isDisplayed()))

        //Game: check timer is visible
        onView(withId(R.id.textViewNameTime)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewTime)).check(matches(isDisplayed()))

        //Game: check buttons and word are invisible
        onView(withId(R.id.button_guessed)).check(matches(not(isDisplayed())))
        onView(withId(R.id.button_pas)).check(matches(not(isDisplayed())))
        onView(withId(R.id.textViewWord)).check(matches(not(isDisplayed())))

        //Game: click button and start game
        onView(withId(R.id.button_to_start)).check(matches(withText("Поехали!")))
        onView(withId(R.id.button_to_start)).perform(click())

        //Game: check buttons and word are visible
        onView(withId(R.id.button_guessed)).check(matches(isDisplayed()))
        onView(withId(R.id.button_pas)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewWord)).check(matches(isDisplayed()))

        //Game: check buttons
        Thread.sleep(1000)
        onView(withId(R.id.button_guessed)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_guessed)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_pas)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_pas)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_pas)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_pas)).perform(click())
        Thread.sleep(1000)

        //Change team(popup)
        onView(withText("OK"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_to_start)).perform(click())
        Thread.sleep(1000)

        onView(withId(R.id.button_guessed)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_guessed)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_guessed)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.button_guessed)).perform(click())
        Thread.sleep(1000)

        //Go to Statistics
        onView(withText("Статистика"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
        Thread.sleep(2000)

        //Go to Main Activity
        onView(withId(R.id.button_go_to_main)).perform(click())
        Thread.sleep(2000)
    }
}