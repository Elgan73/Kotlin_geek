package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.*
import com.aisgorod.kotlin_geek.data.NotesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class NotesViewModel(notesRepository: NotesRepository) : ViewModel() {
    private val notesLiveData = MutableLiveData<ViewState>()

    init {
        notesRepository.observeNotes()
            .onEach { notesLiveData.value = if (it.isEmpty()) ViewState.EMPTY else  ViewState.Value(it)}
            .launchIn(viewModelScope)

    }

    fun observeViewState(): LiveData<ViewState> = notesLiveData
}