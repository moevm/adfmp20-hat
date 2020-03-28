package com.example.hat.entity

data class Vocabular(
    val easy: MutableSet<String>,
    val normal: MutableSet<String>,
    val hard: MutableSet<String>
)