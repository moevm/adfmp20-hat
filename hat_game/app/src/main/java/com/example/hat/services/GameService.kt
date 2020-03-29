package com.example.hat.services

import com.example.hat.entity.CurrentGameSettings
import com.example.hat.exception.NoActiveWordsException
import com.example.hat.exception.VocabularyIsEmptyException
import kotlin.math.roundToInt

class GameService {
    private var game: CurrentGameSettings? = null

    fun setGame(game: CurrentGameSettings) {
        this.game = game
        this.game?.activeWords = mutableSetOf()
        this.game?.activeWords?.addAll(this.game?.vocabular?.toTypedArray() ?: arrayOf())
    }

    fun isTeam1Active(): Boolean {
        return game?.team1Active ?: false
    }

    fun getCurrentScore(): Long? {
        return game?.currentScore
    }

    fun getRandomWordFromVocabulary(): String {
        if (game?.activeWords?.size == 0) {
            if (game?.vocabular?.size == 0) {
                finishRound()
                throw VocabularyIsEmptyException()
            } else {
                throw NoActiveWordsException()
            }
        }
        val index: Int = (game?.activeWords?.size?.minus(1) ?: 1).times(Math.random()).roundToInt()
        game?.currentWord = game?.activeWords?.elementAt(index)?.toUpperCase()
        return game?.currentWord ?: ""
    }

    fun guessWord(word: String) {
        removeWordFromVocabulary(word)
        game?.activeWords?.remove(word)
        updateScore(1)
    }

    fun pasWord(word: String) {
        updateScore(-1)
        game?.activeWords?.remove(word)
    }

    fun finishRound() {
        if (game != null && game?.team1Active!!) {
            game?.team1Score = Pair(
                game?.team1Score?.first,
                game?.team1Score?.second?.plus(game?.currentScore ?: 0) ?: 0
            )
        } else {
            game?.team2Score = Pair(
                game?.team2Score?.first,
                game?.team2Score?.second?.plus(game?.currentScore ?: 0) ?: 0
            )
        }
        game?.team1Active = game?.team1Active?.not() ?: false
        game?.currentScore = 0
        this.game?.activeWords = mutableSetOf()
        this.game?.activeWords?.addAll(this.game?.vocabular?.toTypedArray() ?: arrayOf())
    }

    fun getTeam1Score(): Long {
        return game?.team1Score?.second ?: 0
    }

    fun getTeam2Score(): Long {
        return game?.team2Score?.second ?: 0
    }

    private fun removeWordFromVocabulary(word: String) {
        game?.vocabular?.remove(word)
        game?.currentWord = null
    }

    private fun updateScore(score: Long) {
        game?.currentScore = game?.currentScore?.plus(score)!!
    }
}