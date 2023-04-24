package com.architectcoders.aacboard.model.database

import androidx.room.Embedded
import androidx.room.Relation

data class DashboardEntityWithCellEntities(
    @Embedded
    val dahsboard: DashboardEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "dashboardId"
    )
    val cells: List<CellEntity>
)
