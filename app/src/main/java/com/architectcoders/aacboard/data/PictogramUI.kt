package com.architectcoders.aacboard.data

import android.os.Parcelable
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictogramUI(
    val keyword: String,
    val url: String,
): Parcelable

fun CellPictogram.toUIPictogram():PictogramUI= PictogramUI(keyword, url)
