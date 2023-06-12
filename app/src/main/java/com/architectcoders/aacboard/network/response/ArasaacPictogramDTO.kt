package com.architectcoders.aacboard.network.response

import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.google.gson.annotations.SerializedName

    data class  ArasaacPictogramDTO(
        @SerializedName("_id")
        val id: Int,
        val keywords: List<ArasaacKeywordDTO>,
        val sex: Boolean,
        val violence: Boolean,
    ) {
        companion object {
            private const val CONST_ARASAAC_PICTOGRAM_BASE_URL =
                "https://static.arasaac.org/pictograms/"
        }

        val url: String
            get() = "${CONST_ARASAAC_PICTOGRAM_BASE_URL}$id/${id}_300.png"

        fun toCellPictogram() = CellPictogram(
            keywords.first().keyword,
            url
        )
    }