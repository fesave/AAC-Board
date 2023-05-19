package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.model.PictogramsRepository
import kotlinx.coroutines.flow.Flow

class GetAllDashboardsUseCase(private val repository: PictogramsRepository) :
    suspend () -> Flow<List<Dashboard>> {

    override suspend fun invoke(): Flow<List<Dashboard>> = repository.dashboards
}