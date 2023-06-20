package com.architectcoders.aacboard.domain.use_case.cell.delete

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.repository.PictogramsRepository

class DeleteCellUseCase(
    private val repository: PictogramsRepository
) : suspend (Int, Cell) -> Unit {

    override suspend fun invoke(dashboardId: Int, cell:Cell) {
        return repository.deleteDashboardCell(dashboardId,cell)
    }
}

