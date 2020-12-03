package com.aisgorod.kotlin_geek.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.data.noteId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Note(
    val id: Long = noteId,
    val title: String = "",
    val plot: String = "",
    val color: Color = Color.values().random(),
) : Parcelable


enum class Color {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}

fun Color.mapToColor(context: Context): Int {
    @ColorRes
    val id = when(this) {
        Color.WHITE -> R.color.color_white
        Color.YELLOW -> R.color.color_yellow
        Color.GREEN -> R.color.color_green
        Color.BLUE -> R.color.color_blue
        Color.RED -> R.color.color_red
        Color.VIOLET -> R.color.color_violet
        Color.PINK -> R.color.color_pink
    }

    return ContextCompat.getColor(context, id)
}