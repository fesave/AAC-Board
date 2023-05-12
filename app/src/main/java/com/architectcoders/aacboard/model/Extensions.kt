package com.architectcoders.aacboard.model

import com.architectcoders.aacboard.domain.Pictogram

fun ArasaacPictogram.toDomain() =
    Pictogram(this.keywords.first().keyword, this.url)