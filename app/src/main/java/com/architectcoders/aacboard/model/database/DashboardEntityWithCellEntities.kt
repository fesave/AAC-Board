package com.architectcoders.aacboard.model.database

import androidx.room.Embedded
import androidx.room.Relation

data class DashboardEntityWithCellEntities(
    @Embedded
    val dashboard: DashboardEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "dashboardId"
    )
    val cells: List<CellEntity>
)
