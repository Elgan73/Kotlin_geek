package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

val idRandom = Random(0)
val noteId: Long
    get() = idRandom.nextLong()

object NotesRepositoryImpl : NotesRepository {

    private val notes : MutableList<Note> = mutableListOf()

    private val allNotes = MutableLiveData(getListForNotify())

    override fun observeNotes(): LiveData<List<Note>> {
        return allNotes
    }

    override fun addOrReplace(newNote: Note) {
        notes.find { it.id == newNote.id }?.let {
            notes.remove(it)
        }

        notes.add(newNote)

        allNotes.postValue(getListForNotify())
    }

    private fun getListForNotify(): List<Note> = notes.toMutableList().also {
        it.reverse()
    }

}