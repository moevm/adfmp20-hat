package com.example.hat.services

import com.example.hat.entity.CurrentGameSettings
import kotlin.math.roundToInt

class GameService {
    private var game: CurrentGameSettings? = null

    fun setGame(game: CurrentGameSettings) {
        this.game = game
        this.game?.passedWords = mutableSetOf()
    }

    fun isTeam1Active(): Boolean {
        return game?.team1Active ?: false
    }

    fun getCurrentScore(): Long? {
        return game?.currentScore
    }

    fun getRandomWordFromVocabulary(): String {
        val index: Int = (game?.vocabular?.size ?: 1).times(Math.random()).roundToInt()
        game?.currentWord = game?.vocabular?.elementAt(index)?.toUpperCase()
        return game?.currentWord ?: ""
    }

    fun guessWord(word: String) {
        removeWordFromVocabulary(word)
        updateScore(1)
    }

    fun pasWord(word: String) {
        updateScore(-1)
        this.game?.passedWords?.add(word)
    }

    fun finishRound() {
        if (game != null && game?.team1Active!!) {
            game?.team1Score?.second?.plus(game?.currentScore ?: 0)
        } else {
            game?.team2Score?.second?.plus(game?.currentScore ?: 0)
        }
        game?.team1Active = game?.team1Active?.not() ?: false
        game?.currentScore = 0
        this.game?.passedWords = mutableSetOf()
    }

    private fun removeWordFromVocabulary(word: String) {
        game?.vocabular?.remove(word)
        game?.currentWord = null
    }

    private fun updateScore(score: Long) {
        game?.currentScore = game?.currentScore?.plus(score)!!
    }
}