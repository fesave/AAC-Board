package com.architectcoders.aacboard.domain.repository

interface RegionRepository {
    suspend fun getLastUserRegion(): String
}