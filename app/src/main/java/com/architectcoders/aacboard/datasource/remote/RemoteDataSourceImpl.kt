package com.architectcoders.aacboard.datasource.remote

import android.util.Log
import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.datasource.tryCall
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.Response.*
import com.architectcoders.aacboard.domain.data.arasaac.ArasaacPictogram
import com.architectcoders.aacboard.network.service.ArasaacService

class RemoteDataSourceImpl(private val arasaacService: ArasaacService) : RemoteDataSource {
    override suspend fun searchPictos(
        locale: String,
        searchString: String,
    ): Response<List<ArasaacPictogram>>  = tryCall {
        val list= arasaacService.searchPictos(locale, searchString)
        Log.d("", "Response size: ${list.size}")
        return Success(list.map { it.toArasaacPictogram() })
    }
}