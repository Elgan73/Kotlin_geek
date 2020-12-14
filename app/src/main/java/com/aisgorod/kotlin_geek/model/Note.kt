package com.aisgorod.kotlin_geek.model

import android.os.Parcelable
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.data.noteId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    val id: Long = noteId,
    val title: String = "",
    val plot: String = "",
    var colorC: String = R.color.color_light_grey.toString(),
    var colorInt: Int = 0,
) : Parcelable
