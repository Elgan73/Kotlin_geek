package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.LiveData
import com.aisgorod.kotlin_geek.data.db.DatabaseProvider
import com.aisgorod.kotlin_geek.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.random.Random

val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

class NotesRepositoryImpl(private val provider: DatabaseProvider) : NotesRepository {

    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
            provider.getCurrentUser()
        }

    override fun observeNotes(): Flow<List<Note>> {
        return provider.observeNotes()
    }

    override suspend fun addOrReplace(newNote: Note) = withContext(Dispatchers.IO) {
        provider.addOrReplace(newNote)
    }

    override suspend fun deleteNote(noteId: String) = withContext(Dispatchers.IO) {
        provider.deleteNote(noteId)
    }

}
