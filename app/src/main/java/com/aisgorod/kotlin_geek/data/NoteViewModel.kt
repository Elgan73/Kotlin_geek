package com.aisgorod.kotlin_geek.data

import androidx.lifecycle.ViewModel

class NoteViewModel(var note: Note?) : ViewModel() {


    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(plot = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    override fun onCleared() {
        super.onCleared()
        note?.let {
            NotesRepositoryImpl.addOrReplace(it)
        }
    }

    private fun generateNote() : Note {
        return Note()
    }


}
