package com.aisgorod.kotlin_geek.presentation

import com.aisgorod.kotlin_geek.data.Note

sealed class ViewState {
    data class Value(val notes: List<Note>) : ViewState()
    object EMPTY : ViewState()
}