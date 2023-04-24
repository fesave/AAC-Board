package com.architectcoders.aacboard.model.database

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["dashboardId","row","column"],
        foreignKeys = [ForeignKey(
            entity = DashboardEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("dashboardId"),
            onDelete = ForeignKey.CASCADE
        )]
    )
data class CellEntity(
    val dashboardId: Int,
    val row: Int,
    val column: Int,
    val url: String
)
