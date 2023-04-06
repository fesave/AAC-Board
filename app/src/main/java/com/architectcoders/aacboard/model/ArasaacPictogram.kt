package com.architectcoders.aacboard.model

import com.google.gson.annotations.SerializedName

data class ArasaacPictogram(
    @SerializedName("_id")
    val id: Int,
    val keywords: List<Keyword>,
    val sex: Boolean,
    val violence: Boolean
) {
    fun getUrl(): String {
        return "https://static.arasaac.org/pictograms/${id}/${id}_300.png"
    }
}

data class Keyword(
    val keyword: String
)
