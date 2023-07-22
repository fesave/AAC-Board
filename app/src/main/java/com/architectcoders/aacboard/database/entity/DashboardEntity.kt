package com.architectcoders.aacboard.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells

@Entity
data class DashboardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val rows: Int,
    val columns: Int,
    val image: String,
)

fun DashboardEntity.toDashboard() = Dashboard(
    id = id.toInt(),
    name = name,
    rows = rows,
    columns = columns,
    image = image
)

fun DashboardWithCells.toDashboardEntity(): DashboardEntity =
    DashboardEntity(id.toLong(), name, rows, columns, image)