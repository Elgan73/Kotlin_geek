package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.*
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.data.notesRepository

class NoteViewModel(var note: Note?) : ViewModel() {
    private val showErrorLiveData = MutableLiveData<Boolean>()

    private val lifecycleOwner: LifecycleOwner = LifecycleOwner { viewModelLifecycle }
    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
        it.currentState = Lifecycle.State.RESUMED
    }

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(plot = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }


    fun saveNote() {
        note?.let {note ->
            notesRepository.addOrReplace(note).observe(lifecycleOwner){
                it.onFailure {
                    showErrorLiveData.value = true
                }
            }
        }
    }

    fun deleteNote() {
        note?.let { note ->
            notesRepository.deleteNote(note)
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    override fun onCleared() {
        super.onCleared()
        viewModelLifecycle.currentState = Lifecycle.State.DESTROYED
    }

    private fun generateNote() : Note {
        return Note()
    }
}
