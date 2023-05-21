package com.architectcoders.aacboard.network.response

import com.architectcoders.aacboard.domain.data.arasaac.ArasaacKeyword
import com.architectcoders.aacboard.domain.data.arasaac.ArasaacPictogram

data class ArasaacPictogramDTO(
    val id: Int,
    val arasaacKeywords: List<ArasaacKeyword>,
    val sex: Boolean,
    val violence: Boolean,
) {
    fun toArasaacPictogram() = ArasaacPictogram(
        id = this.id,
        arasaacKeywords = this.arasaacKeywords,
        sex = this.sex,
        violence = this.violence,
    )
}