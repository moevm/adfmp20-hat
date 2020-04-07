package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class PlayersNamesActivityTest {
    @Rule
    @JvmField
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivity() {
        val team1 = "Julia"
        val team2 = "Sergey"
        //Go to PlayersNames
        Espresso.onView(ViewMatchers.withId(R.id.button_new_game)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.button_to_players_names))
            .perform(ViewActions.click())

        //PlayersNames: check text
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name3))
            .check(ViewAssertions.matches(ViewMatchers.withText("Имена команд")))
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.textView3))
            .check(ViewAssertions.matches(ViewMatchers.withText("Команда 1:")))
        Espresso.onView(ViewMatchers.withId(R.id.textView3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.textView4))
            .check(ViewAssertions.matches(ViewMatchers.withText("Команда 2:")))
        Espresso.onView(ViewMatchers.withId(R.id.textView4))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //PlayersNames: enter team1 name
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name))
            .perform(ViewActions.clearText(), ViewActions.typeText(team1))
        Thread.sleep(500)

        //PlayersNames: enter team2 name
        Espresso.onView(ViewMatchers.withId(R.id.textView_player_name2))
            .perform(ViewActions.clearText(), ViewActions.typeText(team2))
        Thread.sleep(1000)

        Espresso.pressBack()
    }
}