package com.example.hat.entity

import java.io.Serializable

data class GameSettings(
    var vocabular: MutableList<String>? = null,
    var secondsPerStep: Long? = null,
    var team1: String? = null,
    var team2: String? = null
) : Serializable