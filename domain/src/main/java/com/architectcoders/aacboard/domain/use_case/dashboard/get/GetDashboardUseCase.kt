package com.architectcoders.aacboard.domain.use_case.dashboard.get

import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import kotlinx.coroutines.flow.Flow

class GetDashboardUseCase(
    private val repository: PictogramsRepository
) : suspend (Int) -> Flow<DashboardWithCells?> {

    override suspend fun invoke(id: Int): Flow<DashboardWithCells?> {
        return repository.getDashBoardWithCells(id)
    }
}


