package com.architectcoders.aacboard.database.dao

import com.architectcoders.aacboard.database.entity.CellEntity
import com.architectcoders.aacboard.database.entity.DashboardEntity

fun buildDashboard(
    id: Long = 1L,
    name: String = "dummmy",
    rows: Int = 2,
    columns: Int = 3,
    image: String = ""
) =
    DashboardEntity(id, name, rows, columns, image)

@SuppressWarnings("LongParameterList")
suspend fun buildAndPopulateDashboard(
    dashboardDao: DashboardDao,
    id: Int = 1,
    name: String = "dummmy",
    rows: Int = 2,
    columns: Int = 3,
    image: String = ""
) {
    dashboardDao.insertDashboard(buildDashboard(id.toLong(), name, rows, columns, image))
    val cellList = mutableListOf<CellEntity>()
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            cellList.add(buildCell(id, i, j))
        }
    }
    dashboardDao.insertCells(cellList)
}

fun buildCell(
    dashboardId: Int,
    row: Int,
    column: Int,
    keyword: String? = null,
    url: String? = null
) =
    CellEntity(
        dashboardId.toLong(),
        row,
        column,
        keyword ?: "keyword $dashboardId ($row , $column)",
        url ?: "dummyUrl"
    )