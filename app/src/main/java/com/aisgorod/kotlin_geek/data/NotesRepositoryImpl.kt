package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.LiveData
import com.aisgorod.kotlin_geek.data.db.DatabaseProvider
import com.aisgorod.kotlin_geek.data.db.FireStoreDatabaseProvider
import com.aisgorod.kotlin_geek.model.Note
import kotlin.random.Random

val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

class NotesRepositoryImpl(private val provider: DatabaseProvider) : NotesRepository {

    override fun getCurrentUser() = provider.getCurrentUser()

    override fun observeNotes(): LiveData<List<Note>> {
        return provider.observeNotes()
    }

    override fun addOrReplace(newNote: Note): LiveData<Result<Note>> {
        return provider.addOrReplace(newNote)
    }

    override fun deleteNote(note: Note) {
        return provider.removeNote(note)
    }
}
