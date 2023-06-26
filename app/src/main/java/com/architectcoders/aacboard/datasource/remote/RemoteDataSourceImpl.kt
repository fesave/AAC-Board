package com.architectcoders.aacboard.datasource.remote

import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.datasource.tryCall
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.Response.Success
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.network.service.ArasaacService

class RemoteDataSourceImpl(private val arasaacService: ArasaacService) : RemoteDataSource {
    override suspend fun searchPictos(
        locale: String,
        searchString: String,
    ): Response<List<CellPictogram>> = tryCall {
        val list = arasaacService.searchPictos(locale, searchString)
        return Success(list.map { it.toCellPictogram() })
    }
}