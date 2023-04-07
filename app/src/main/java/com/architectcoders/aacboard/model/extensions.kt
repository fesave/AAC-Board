package com.architectcoders.aacboard.model

fun ArasaacPictogram.toDomainPictogram() =
    DomainPictogram(this.keywords.first().keyword, this.url)

