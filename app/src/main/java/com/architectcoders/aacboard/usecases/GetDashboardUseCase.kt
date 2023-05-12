package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.domain.DashboardCell
import com.architectcoders.aacboard.domain.Pictogram

class GetDashboardUseCase : suspend (Int) -> Dashboard? {

    override suspend fun invoke(id: Int): Dashboard? {
        return getDashboard(id)
    }
}

private fun getDashboard(id: Int): Dashboard? {
    return when (id) {
        1 -> dashboardOne
        2 -> dashboardTwo
        3 -> dashboardThree
        else -> null
    }
}

val dashboardOne = generateDashboard(1, 4, 4, 2517)
val dashboardTwo = generateDashboard(2, 5, 5, 2540)
val dashboardThree = generateDashboard(3, 3, 4, 2560)


private fun generateDashboard(id: Int, columns: Int, rows: Int, startingId: Int): Dashboard {
    return Dashboard(
        id = id,
        name = "$startingId",
        rows = rows,
        columns = columns,
        cells = generateCells(startingId, (startingId -1 + (columns * rows)))
    )
}

private fun generateCells(startingId: Int, endingId: Int): List<DashboardCell> {
    val cells = mutableListOf<DashboardCell>()
    (startingId..endingId).forEach {
        cells.add(
            DashboardCell(
                0,
                0,
                Pictogram("", "https://static.arasaac.org/pictograms/$it/${it}_500.png")
            )
        )
    }
    return cells.toList()
}

