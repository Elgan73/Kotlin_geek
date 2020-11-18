package com.aisgorod.kotlin_geek.data

data class Note(
    val title: String,
    val plot: String,
    val color: Int = 0x0000000,
)