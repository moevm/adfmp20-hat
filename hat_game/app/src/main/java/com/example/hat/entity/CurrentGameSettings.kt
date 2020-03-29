package com.example.hat.entity

data class CurrentGameSettings(
    var team1Score: Pair<String?, Long>,
    var team2Score: Pair<String?, Long>,
    var team1Active: Boolean = true,
    var vocabular: MutableSet<String>? = null,
    var activeWords: MutableSet<String>? = null,
    var currentWord: String? = null,
    var currentScore: Long = 0
)