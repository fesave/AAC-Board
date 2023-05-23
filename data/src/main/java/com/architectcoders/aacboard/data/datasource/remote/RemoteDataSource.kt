package com.architectcoders.aacboard.data.datasource.remote

import com.architectcoders.aacboard.domain.data.arasaac.ArasaacPictogram

interface RemoteDataSource {

    suspend fun searchPictos(locale:String, searchString:String): List<ArasaacPictogram>
}