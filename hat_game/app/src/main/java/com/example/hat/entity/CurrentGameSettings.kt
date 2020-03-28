package com.example.hat.entity

data class CurrentGameSettings(
    val team1Score: Pair<String?, Long>,
    val team2Score: Pair<String?, Long>,
    var team1Active: Boolean = true
)