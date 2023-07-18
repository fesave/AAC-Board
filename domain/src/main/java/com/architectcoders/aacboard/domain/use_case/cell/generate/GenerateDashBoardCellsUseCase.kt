package com.architectcoders.aacboard.domain.use_case.cell.generate

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram

class GenerateDashBoardCellsUseCase : (Int, Int) -> List<Cell> {
    override fun invoke(rows: Int, columns: Int): List<Cell> {
        val cells = mutableListOf<Cell>()
        var id = 0
        (0 until rows).forEachIndexed { rowIndex, _ ->
            (0 until columns).forEachIndexed { columnIndex, _ ->
                cells.add(Cell(rowIndex, columnIndex, null))
                id++
            }
        }
        return cells.toList()
    }
}