package com.architectcoders.aacboard.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells

@Entity
data class DashboardEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val rows: Int,
    val columns: Int,
)

fun DashboardEntity.toDashboard() = Dashboard(
    id = id,
    name = name,
    rows = rows,
    columns = columns,
)

fun DashboardWithCells.toDashboardEntity(): DashboardEntity =
    DashboardEntity(id, name, rows, columns)