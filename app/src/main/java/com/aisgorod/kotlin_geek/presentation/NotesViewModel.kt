package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.aisgorod.kotlin_geek.data.notesRepository


class NotesViewModel : ViewModel() {

    fun observeViewState(): LiveData<ViewState> = notesRepository.observeNotes()
        .map {
        if(it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
    }
}