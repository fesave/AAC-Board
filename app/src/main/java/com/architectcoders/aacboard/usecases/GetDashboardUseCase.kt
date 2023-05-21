package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.domain.DashboardWithCells
import com.architectcoders.aacboard.model.PictogramsRepository

class GetDashboardUseCase(
    private val repository: PictogramsRepository
) : suspend (Int) -> DashboardWithCells? {

    override suspend fun invoke(id: Int): DashboardWithCells? {
        return repository.getDashBoardWithCells(id)
    }
}


