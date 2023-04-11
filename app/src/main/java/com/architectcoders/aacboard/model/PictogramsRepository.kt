package com.architectcoders.aacboard.model

import java.util.*

class PictogramsRepository {

    suspend fun searchPictograms(searchString: String):List<DomainPictogram> {
        val locale= Locale.getDefault().getLanguage()
        val arasaacPictograms = RemoteConnection.service.searchPictos(locale, searchString)
        return arasaacPictograms.map {it.toDomainPictogram()}
    }

}