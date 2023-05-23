package com.architectcoders.aacboard.domain.use_case.dashboard.get

import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.repository.PictogramsRepository

class GetDashboardUseCase(
    private val repository: PictogramsRepository
) : suspend (Int) -> DashboardWithCells? {

    override suspend fun invoke(id: Int): DashboardWithCells? {
        return repository.getDashBoardWithCells(id)
    }
}


