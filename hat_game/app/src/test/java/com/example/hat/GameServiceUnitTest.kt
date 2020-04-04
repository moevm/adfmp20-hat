package com.example.hat

import com.example.hat.entity.CurrentGameSettings
import com.example.hat.services.GameService
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
* Tests for GameService class
 */
class GameServiceUnitTest {

    private var gameService: GameService? = null

    private var currentGameSettings: CurrentGameSettings? = null

    @Before
    fun init() {
        currentGameSettings = CurrentGameSettings(
            Pair("team1", 0),
            Pair("team2", 0),
            vocabular = mutableSetOf("one", "two", "three")
        )
        gameService = GameService()
        currentGameSettings?.let { gameService?.setGame(it) }
    }

    @Test
    fun initGameServiceTest() {
        assertTrue(gameService?.getTeam1Score() == currentGameSettings?.team1Score?.second)
        assertTrue(gameService?.getTeam2Score() == currentGameSettings?.team2Score?.second)
        assertTrue(gameService!!.isTeam1Active())
    }

    @Test
    fun getRandomWordFromVocabularyTest() {
        assertFalse(gameService!!.getRandomWordFromVocabulary().isEmpty())
    }

    @Test
    fun guessWordTest() {
        val word = "two"
        val remainingWords = mutableSetOf("one", "three")
        gameService?.guessWord(word)
        assertEquals(currentGameSettings!!.vocabular, remainingWords)
        assertEquals(currentGameSettings!!.activeWords, remainingWords)
        assertEquals(currentGameSettings!!.currentScore, 1)
    }

    @Test
    fun passWordTest() {
        val word = "two"
        val remainingWords = mutableSetOf("one", "three")
        gameService?.pasWord(word)
        assertEquals(currentGameSettings!!.vocabular!!.size, 3)
        assertEquals(currentGameSettings!!.activeWords, remainingWords)
        assertEquals(currentGameSettings!!.currentScore, -1)
    }

    @Test
    fun guessWordAndFinishRoundTest() {
        val word = "two"
        val remainingWords = mutableSetOf("one", "three")
        gameService?.guessWord(word)
        gameService?.finishRound()
        assertFalse(currentGameSettings!!.team1Active)
        assertTrue(currentGameSettings!!.currentScore == 0L)
        assertEquals(currentGameSettings!!.vocabular, remainingWords)
        assertEquals(currentGameSettings!!.activeWords, remainingWords)
    }
}
