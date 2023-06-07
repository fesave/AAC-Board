package com.architectcoders.aacboard.network.response

import com.architectcoders.aacboard.domain.data.arasaac.ArasaacKeyword
import com.architectcoders.aacboard.domain.data.arasaac.ArasaacPictogram
import com.google.gson.annotations.SerializedName

data class  ArasaacPictogramDTO(
    @SerializedName("_id")
    val id: Int,
    val keywords: List<ArasaacKeyword>?,
    val sex: Boolean,
    val violence: Boolean,
) {
    fun toArasaacPictogram() = ArasaacPictogram(
        id = this.id,
        arasaacKeywords = this.keywords ?: emptyList<ArasaacKeyword>(),
        sex = this.sex,
        violence = this.violence,
    )
}