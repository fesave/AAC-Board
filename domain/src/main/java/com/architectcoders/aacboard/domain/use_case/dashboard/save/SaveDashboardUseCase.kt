package com.architectcoders.aacboard.domain.use_case.dashboard.save

import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.repository.PictogramsRepository

class SaveDashboardUseCase(private val repository: PictogramsRepository) :
    suspend (DashboardWithCells) -> Unit {

    override suspend fun invoke(dashboard: DashboardWithCells) {
        //repository.deleteDashboardCellContent(dashboard.id, dashboard.cells.takeLast(2))
        repository.saveDashboard(dashboard)
    }
}