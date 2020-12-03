package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.LiveData
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.model.User

interface NotesRepository {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplace(newNote: Note): LiveData<Result<Note>>
    fun deleteNote(note: Note)
}