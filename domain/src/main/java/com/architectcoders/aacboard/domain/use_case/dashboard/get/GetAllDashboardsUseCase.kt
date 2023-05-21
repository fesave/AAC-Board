package com.architectcoders.aacboard.domain.use_case.dashboard.get

import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import kotlinx.coroutines.flow.Flow

class GetAllDashboardsUseCase(private val repository: PictogramsRepository) :
    suspend () -> Flow<List<Dashboard>> {

    override suspend fun invoke(): Flow<List<Dashboard>> = repository.getDashboards()
}