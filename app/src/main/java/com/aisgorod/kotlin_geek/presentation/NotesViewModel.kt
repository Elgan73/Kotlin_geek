package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.aisgorod.kotlin_geek.data.NotesRepositoryImpl

class NotesViewModel : ViewModel() {

    fun observeViewState(): LiveData<ViewState> = NotesRepositoryImpl.observeNotes()
        .map {
        if(it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
    }
}