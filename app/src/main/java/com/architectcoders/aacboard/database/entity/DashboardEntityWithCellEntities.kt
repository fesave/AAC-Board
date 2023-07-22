package com.architectcoders.aacboard.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells

data class DashboardEntityWithCellEntities(
    @Embedded
    val dashboard: DashboardEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "dashboardId",
    )
    val cells: List<CellEntity>,
)

fun DashboardEntityWithCellEntities.toDashboardWithCells(): DashboardWithCells {
    return DashboardWithCells(
        id = dashboard.id.toInt(),
        name = dashboard.name,
        rows = dashboard.rows,
        columns = dashboard.columns,
        image = dashboard.image,
        cells = cells.map { it.toCell() },
    )
}