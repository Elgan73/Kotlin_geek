package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.*
import com.aisgorod.kotlin_geek.data.NotesRepository
import com.aisgorod.kotlin_geek.model.Note

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {
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

    fun updateColor(color: Int) {
        note = (note ?: generateNote()).copy(colorInt = color)
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
            notesRepository.deleteNote(note.id.toString())
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
