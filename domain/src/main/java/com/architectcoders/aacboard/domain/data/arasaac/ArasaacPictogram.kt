package com.architectcoders.aacboard.domain.data.arasaac

import com.architectcoders.aacboard.domain.data.cell.CellPictogram


data class ArasaacPictogram(
    val id: Int,
    val arasaacKeywords: List<ArasaacKeyword>,
    val sex: Boolean,
    val violence: Boolean,
) {
    val url: String
        get() = "$CONST_ARASAAC_PICTOGRAM_BASE_URL$id/${id}_300.png"

    fun toPictogram() = CellPictogram(
        this.arasaacKeywords.first().keyword,
        this.url
    )

    companion object {
        private const val CONST_ARASAAC_PICTOGRAM_BASE_URL =
            "https://static.arasaac.org/pictograms/"
    }
}