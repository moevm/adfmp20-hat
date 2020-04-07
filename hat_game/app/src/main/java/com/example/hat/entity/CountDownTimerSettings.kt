package com.example.hat.entity


data class CountDownTimerSettings(
    var currentValue: Long = 0,
    var remaining: Long = 0,
    var isPaused: Boolean = true
)