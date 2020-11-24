package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.LiveData

interface NotesRepository {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplace(newNote: Note)
}