package com.architectcoders.aacboard.domain.repository

interface RegionRepository {
    suspend fun getUserLanguage(): String
}