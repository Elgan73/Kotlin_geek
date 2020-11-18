package com.aisgorod.kotlin_geek.data

object NotesRepositoryImpl : NotesRepository {

    private val notes = listOf(
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xfff06292.toInt()
        ),
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xff9575cd.toInt()
        ),
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xff64b5f6.toInt()
        ),
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xff4db6ac.toInt()
        ),
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xffb2ff59.toInt()
        ),
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xffffeb3b.toInt()
        ),
        Note(
            title = "Моя первая заметка",
            plot = "Kotlin очень краткий, но при этом выразительный язык",
            color = 0xffff6e40.toInt()
        )
    )

    override fun getNotes(): List<Note> {
        return notes
    }

}