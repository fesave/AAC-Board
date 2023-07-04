package com.architectcoders.aacboard.domain.use_case.cell.get


import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.repository.PictogramsRepository

class GetCellUseCase(
    private val repository: PictogramsRepository
) : suspend (Int, Int, Int) -> Cell? {

    override suspend fun invoke(dashboardId: Int, row: Int, column: Int): Cell? =
        repository.getDashboardCell(dashboardId,row,column)

}

