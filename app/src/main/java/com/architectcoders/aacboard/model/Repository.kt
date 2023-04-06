package com.architectcoders.aacboard.model

class Repository {

    suspend fun searchPictograms(searchString: String):List<DomainPictogram> {
        val arasaacPictograms = RemoteConnection.service.searchPictos(searchString)
        return arasaacPictograms.map {
            DomainPictogram(it.keywords.first().keyword, it.getUrl())
        }
    }

}