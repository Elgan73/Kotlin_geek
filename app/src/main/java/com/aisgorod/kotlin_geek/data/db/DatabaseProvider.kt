package com.aisgorod.kotlin_geek.data.db

import androidx.lifecycle.LiveData
import com.aisgorod.kotlin_geek.data.Note

interface DatabaseProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplace(newNote: Note): LiveData<Result<Note>>
    fun removeNote(note: Note)
}