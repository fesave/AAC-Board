package com.architectcoders.aacboard.datasource.local

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells

fun buildDashboardWithCells(
    id: Int = 1,
    name: String = "dummmy",
    rows: Int = 2,
    columns: Int = 3,
    image: String = ""
): DashboardWithCells {
    val cellList = mutableListOf<Cell>()
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            cellList.add(
                buildCell(i, j, "Key $id ($i, $j)", "url $i $j")
            )
        }
    }
    return DashboardWithCells(id, name, rows, columns, image, cellList)
}

fun buildCell(
    row: Int,
    column: Int,
    keyword: String? = null,
    url: String? = null
) = Cell(
    row,
    column,
    CellPictogram(keyword ?: "", url ?: "")
)