package com.aisgorod.kotlin_geek.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.data.noteId
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


@Parcelize
data class Note(
    val id: Long = noteId,
    val title: String = "",
    val plot: String = "",
    var _color: Int = 0,
) : Parcelable
{
    val color: Any by FirebaseColorDelegate(_color)
}


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

class FirebaseColorDelegate(private var clr: Int): ReadWriteProperty<Any?, Any> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Any {
        return clr
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Any) {
        Log.d("FirebaseColorDelegate", "calling setter of ${property.name} with value $value")
        clr = helpSet(value)
    }

    private fun <T> helpSet(t: T): Int {
        return when (t) {
            is Color -> t.name.toInt()
            is Int -> t
            else -> throw IllegalArgumentException()
        }
    }

    private companion object : Parceler<Note> {
        override fun create(parcel: Parcel): Note {
            TODO("Not yet implemented")
        }

        override fun Note.write(parcel: Parcel, flags: Int) {
            TODO("Not yet implemented")
        }

    }

}