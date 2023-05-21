package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.domain.DashboardWithCells
import com.architectcoders.aacboard.model.PictogramsRepository

class SaveDashboardUseCase(private val repository: PictogramsRepository) :
    suspend (DashboardWithCells) -> Unit {

    override suspend fun invoke(dashboard: DashboardWithCells) {
        //repository.deleteDashboardCellContent(dashboard.id, dashboard.cells.takeLast(2))
        repository.saveDashboard(dashboard)
    }
}