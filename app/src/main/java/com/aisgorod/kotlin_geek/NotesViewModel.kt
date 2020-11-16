package com.aisgorod.kotlin_geek

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


fun interface Listener {
    fun handle(string: String)
}

class Model(private val listener: Listener) {
    private fun getData(): String {
        return "Hello!!!"
    }

    fun calculateValue() {
        listener.handle(getData())
    }
}

class NotesViewModel : ViewModel() {
    private val model = Model {
        textData.value = it
    }

    private val textData = MutableLiveData("WOW")

    fun observeTextData(): LiveData<String> = textData

    fun buttonClicked() {
        model.calculateValue()
    }
}