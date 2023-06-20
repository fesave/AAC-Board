package com.architectcoders.aacboard.data.datasource.remote

import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.cell.CellPictogram

interface RemoteDataSource {
    suspend fun searchPictos(locale:String, searchString:String): Response<List<CellPictogram>>
}