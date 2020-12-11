package com.aisgorod.kotlin_geek.presentation

import androidx.lifecycle.*
import com.aisgorod.kotlin_geek.data.NotesRepository
import com.aisgorod.kotlin_geek.errors.NoAuthException
import com.aisgorod.kotlin_geek.model.User
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class SplashViewModel(private val repository: NotesRepository) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            viewStateLiveData.value = if (user != null) {
                SplashViewState.Auth
            } else {
                SplashViewState.Error(error = NoAuthException())
            }
        }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData
}