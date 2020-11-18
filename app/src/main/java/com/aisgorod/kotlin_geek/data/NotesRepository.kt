package com.aisgorod.kotlin_geek.data

interface NotesRepository {
    fun getNotes(): List<Note>
}