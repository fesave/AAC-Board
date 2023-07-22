package com.architectcoders.aacboard.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram

@Entity(
    primaryKeys = ["dashboardId", "row", "column"],
    foreignKeys = [
        ForeignKey(
            entity = DashboardEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("dashboardId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class CellEntity(
    val dashboardId: Long,
    val row: Int,
    val column: Int,
    val url: String?,
    val text: String?,
)

fun CellEntity.toCell() = Cell(
    row,
    column,
    cellPictogram = if (url == null || text == null) null else CellPictogram(text, url),
)

fun Cell.toCellEntity(dashboardId: Int): CellEntity =
    CellEntity(dashboardId.toLong(), row, column, cellPictogram?.url, cellPictogram?.keyword)