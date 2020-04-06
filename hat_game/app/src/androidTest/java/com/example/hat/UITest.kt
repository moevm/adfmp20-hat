package com.example.hat

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
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

        //Main Activity: check button_to_statistics
        onView(withId(R.id.button_to_statistics)).check(matches(withText("Статистика")))
        onView(withId(R.id.button_to_statistics)).check(matches(isDisplayed()))

        //Main Activity: check button_rules
        onView(withId(R.id.button_rules)).check(matches(withText("Правила")))
        onView(withId(R.id.button_rules)).check(matches(isDisplayed()))

        //Main Activity: go to Rules
        onView(withId(R.id.button_rules)).perform(click())

        //Rules: check text
        onView(withId(R.id.textViewRules)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        Espresso.pressBack()

        //Main Activity: go to Statistics
        onView(withId(R.id.button_to_statistics)).perform(click())

        //Statistics: check text
        onView(withId(R.id.textView_stat)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        Espresso.pressBack()

        //Main Activity: check button_new_game
        onView(withId(R.id.button_new_game)).check(matches(withText("Новая игра")))
        onView(withId(R.id.button_new_game)).check(matches(isDisplayed()))

        //Main Activity: click button and go to Settings
        onView(withId(R.id.button_new_game)).perform(click())
        onView(withId(R.id.textView_settings)).check(matches(isDisplayed()))

        //Settings: check text
        onView(withId(R.id.textView_settings)).check(matches(withText("Настройки")))
        onView(withId(R.id.textView_settings)).check(matches(isDisplayed()))

        //Settings: check spinner for time
        onView(withId(R.id.textView2)).check(matches(withText("Количество секунд на ход:")))
        onView(withId(R.id.textView2)).check(matches(isDisplayed()))

        onView(withId(R.id.spinner_time_for_step)).perform(click())
        onData(anything()).atPosition(0).perform(click())

        //Settings: check spinner for number of words
        onView(withId(R.id.textView_words_in_vocabular)).check(matches(withText("Количество слов в шляпе:")))
        onView(withId(R.id.textView_words_in_vocabular)).check(matches(isDisplayed()))

        onView(withId(R.id.spinner_words_in_vocabular)).perform(click())
        onData(anything()).atPosition(0).perform(click())

        //Settings: check modes
        onView(withId(R.id.textView5)).check(matches(withText("Уровень сложности:")))
        onView(withId(R.id.textView5)).check(matches(isDisplayed()))

        onView(withId(R.id.checkBoxEasy)).check(matches(withText("Легкий")))
        onView(withId(R.id.checkBoxEasy)).check(matches(isDisplayed()))

        onView(withId(R.id.checkBoxNorm)).check(matches(withText("Средний")))
        onView(withId(R.id.checkBoxNorm)).check(matches(isDisplayed()))
        onView(withId(R.id.checkBoxNorm)).perform(click())

        onView(withId(R.id.checkBoxHard)).check(matches(withText("Сложный")))
        onView(withId(R.id.checkBoxHard)).check(matches(isDisplayed()))

        //Settings: click button and go to PlayersNames
        onView(withId(R.id.button_to_players_names)).check(matches(withText("Продолжить")))
        onView(withId(R.id.button_to_players_names)).perform(click())

        //PlayersNames: check text
        onView(withId(R.id.textView_player_name3)).check(matches(withText("Имена команд")))
        onView(withId(R.id.textView_player_name3)).check(matches(isDisplayed()))

        onView(withId(R.id.textView3)).check(matches(withText("Команда 1:")))
        onView(withId(R.id.textView3)).check(matches(isDisplayed()))

        onView(withId(R.id.textView4)).check(matches(withText("Команда 2:")))
        onView(withId(R.id.textView4)).check(matches(isDisplayed()))

        //PlayersNames: enter team1 name
        onView(withId(R.id.textView_player_name)).perform(clearText(), typeText(team1))
        Thread.sleep(500)

        //PlayersNames: enter team2 name
        onView(withId(R.id.textView_player_name2)).perform(clearText(), typeText(team2))
        Thread.sleep(1000)

        //PlayersNames: click button and go to Add Word
        onView(withId(R.id.button_to_mode)).check(matches(withText("Продолжить")))
        onView(withId(R.id.button_to_mode)).perform(click())

        //Add Word: check text
        onView(withId(R.id.textView_player_name4)).check(matches(isDisplayed()))
        onView(withId(R.id.textView_player_name5)).check(matches(withText("Количество слов в словаре: ")))
        onView(withId(R.id.textView_player_name5)).check(matches(isDisplayed()))

        //Add Word: enter word
        onView(withId(R.id.editText_to_add_word)).perform(clearText(), typeText("hat"))
        Thread.sleep(500)
        onView(withId(R.id.button_to_add_word)).perform(click())
        Thread.sleep(500)

        //Add Word: click button and go to GAME
        onView(withId(R.id.button_continue_to_start)).check(matches(withText("Продолжить")))
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

        onView(withId(R.id.popup_button_ok)).perform(click())
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
        onView(withId(R.id.popup_button_statistics)).perform(click())
        Thread.sleep(2000)

        //Go to Main Activity
        onView(withId(R.id.button_go_to_main)).perform(click())
        Thread.sleep(2000)
    }
}