package com.example.hat.entity

data class CurrentGameSettings(
    val team1Score: Pair<String?, Long>,
    val team2Score: Pair<String?, Long>,
    var team1Active: Boolean = true,
    var vocabular: MutableSet<String>? = null,
    var passedWords: MutableSet<String>? = null,
    var currentWord: String? = null,
    var currentScore: Long = 0
)