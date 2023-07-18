package com.architectcoders.aacboard.ui.utils

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells

@Suppress("MagicNumber")
val dashboardOne = generateDashboard(1, "Dashboard 1 4x4", 4, 4, 2517)

@Suppress("MagicNumber")
val dashboardTwo = generateDashboard(2, "Dashboard 2 5x5", 5, 5, 2540)

@Suppress("MagicNumber")
val dashboardThree = generateDashboard(3, "Dashboard 3 3x4", 3, 4, 2560)

private fun generateDashboard(
    id: Int,
    name: String,
    columns: Int,
    rows: Int,
    startingId: Int,
): DashboardWithCells {
    return DashboardWithCells(
        id = id,
        name = name,
        rows = rows,
        columns = columns,
        image = "",
        cells = generateCells(startingId, rows, columns),
    )
}

private fun generateCells(startingId: Int, rows: Int, columns: Int): List<Cell> {
    val cells = mutableListOf<Cell>()
    var id = startingId
    (0 until rows).forEachIndexed { rowIndex, _ ->
        (0 until columns).forEachIndexed { columnIndex, _ ->
            cells.add(
                Cell(
                    rowIndex,
                    columnIndex,
                    CellPictogram(
                        "Pictogram $id",
                        "https://static.arasaac.org/pictograms/$id/${id}_500.png",
                    ),
                ),
            )
            id++
        }
    }
    return cells.toList()
}