package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aisgorod.kotlin_geek.data.NotesRepositoryImpl

class NotesViewModel : ViewModel() {
    private val viewStateLiveData = MutableLiveData<ViewState>(ViewState.EMPTY)

    init {
        val notes = NotesRepositoryImpl.getNotes()
        viewStateLiveData.value = ViewState.Value(notes)
    }

    fun observeViewState(): LiveData<ViewState> = viewStateLiveData
}