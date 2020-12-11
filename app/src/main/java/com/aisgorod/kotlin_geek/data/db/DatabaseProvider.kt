package com.aisgorod.kotlin_geek.data.db

import androidx.lifecycle.LiveData
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.model.User

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplace(newNote: Note): LiveData<Result<Note>>
    fun deleteNote(noteId: String): LiveData<Result<Unit>>
}