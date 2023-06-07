package com.architectcoders.aacboard.datasource.remote

import android.util.Log
import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.domain.data.arasaac.ArasaacPictogram
import com.architectcoders.aacboard.network.service.ArasaacService

class RemoteDataSourceImpl(private val arasaacService: ArasaacService) : RemoteDataSource {
    override suspend fun searchPictos(
        locale: String,
        searchString: String,
    ): List<ArasaacPictogram> {
        val list= arasaacService.searchPictos(locale, searchString)
        Log.d("", "Response size: ${list.size}")
        return list.map {
            it.toArasaacPictogram()
        }
    }
}