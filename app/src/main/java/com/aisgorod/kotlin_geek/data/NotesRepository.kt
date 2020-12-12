package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.LiveData
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.model.User
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplace(newNote: Note)
    suspend fun deleteNote(noteId: String)
}