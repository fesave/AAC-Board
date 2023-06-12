package com.architectcoders.aacboard.data.datasource.local

interface LocationDataSource {
    suspend fun getLastUserRegion():String?
}