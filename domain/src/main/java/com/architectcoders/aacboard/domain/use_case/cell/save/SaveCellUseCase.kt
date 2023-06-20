package com.architectcoders.aacboard.domain.use_case.cell.save

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.repository.PictogramsRepository

class SaveCellUseCase(
    private val repository: PictogramsRepository
) : suspend (Int, Cell) -> Unit {

    override suspend fun invoke(dashboardId: Int, cell: Cell) {
        repository.saveDashboardCell(dashboardId, cell)
    }
}
