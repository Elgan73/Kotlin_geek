package com.aisgorod.kotlin_geek.presentation

import android.util.Log
import androidx.lifecycle.*
import com.aisgorod.kotlin_geek.data.NotesRepository
import com.aisgorod.kotlin_geek.model.Note
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {


    private val showErrorLiveData = MutableLiveData<Boolean>()

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
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.addOrReplace(noteValue)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.deleteNote(noteValue.id.toString())
            } catch (th: Throwable) {
                Log.d("ExceptionCoroutine", "Error delete note" )
            }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    private fun generateNote() : Note {
        return Note()
    }
}
