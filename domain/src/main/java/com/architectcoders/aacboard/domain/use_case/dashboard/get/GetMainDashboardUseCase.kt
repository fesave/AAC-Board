package com.architectcoders.aacboard.domain.use_case.dashboard.get

import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import kotlinx.coroutines.flow.Flow

class GetMainDashboardUseCase(
    private val repository: PictogramsRepository,
): suspend () -> Flow<DashboardWithCells?> {

    override suspend fun invoke(): Flow<DashboardWithCells?> = repository.getMainDashboard()
}
