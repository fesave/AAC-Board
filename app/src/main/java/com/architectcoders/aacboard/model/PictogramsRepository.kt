package com.architectcoders.aacboard.model

class PictogramsRepository {

    companion object {
        private const val DEFAULT_LOCALE = "es"
    }

    suspend fun searchPictograms(searchString: String):List<DomainPictogram> {
        val arasaacPictograms = RemoteConnection.service.searchPictos(DEFAULT_LOCALE, searchString)
        return arasaacPictograms.map {it.toDomainPictogram()}
    }

}