package com.architectcoders.aacboard.datasource.remote

import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.domain.data.arasaac.ArasaacPictogram
import com.architectcoders.aacboard.network.service.ArasaacService

class RemoteDataSourceImpl(private val arasaacService: ArasaacService) : RemoteDataSource {
    override suspend fun searchPictos(
        locale: String,
        searchStrnig: String,
    ): List<ArasaacPictogram> = arasaacService.searchPictos(locale, searchStrnig).map {
        it.toArasaacPictogram()
    }
}