package com.architectcoders.aacboard.model.datasource

import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.domain.Cell
import com.architectcoders.aacboard.domain.DashboardWithCells
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.model.database.CellEntity
import com.architectcoders.aacboard.model.database.DashboardDao
import com.architectcoders.aacboard.model.database.DashboardEntity
import com.architectcoders.aacboard.model.database.DashboardEntityWithCellEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DashboardLocalDataSource(private val dashboardDao: DashboardDao) {

    val dashboards: Flow<List<Dashboard>> = dashboardDao.getDashboards().map { list ->
        list.map { dashboard ->
            dashboard.toDomain()
        }
    }

    suspend fun getDashBoardWithCells(id: Int): DashboardWithCells? =
        dashboardDao.getDashboard(id)?.toDomain()

    suspend fun saveDashboard(dashboard: DashboardWithCells) {
        dashboardDao.insertDashboard(dashboard.toEntity())
        dashboardDao.insertCells(dashboard.cells.map { it.toEntity(dashboard.id) })
    }

    suspend fun deleteDashboard(id: Int) {
        dashboardDao.deleteDashboardEntity(id)
    }

    suspend fun deleteCells(dashboardId: Int, cells: List<Cell>) {
        dashboardDao.deleteCells(cells.map { it.toEntity(dashboardId) })
    }

    suspend fun deleteCellsContent(dashboardId: Int, cells: List<Cell>) {
        dashboardDao.insertCells(cells.map {
            it.toEntity(dashboardId).copy(url = null, text = null)
        })
    }
}

fun DashboardEntityWithCellEntities.toDomain(): DashboardWithCells {
    return DashboardWithCells(
        id = dashboard.id,
        name = dashboard.name,
        rows = dashboard.rows,
        columns = dashboard.columns,
        cells = cells.map { it.toDomain() }
    )
}

fun CellEntity.toDomain(): Cell {
    return Cell(
        row,
        column,
        pictogram = if (url == null || text == null) null else Pictogram(text, url)
    )
}

fun DashboardEntity.toDomain() =
    Dashboard(
        id = id,
        name = name,
        rows = rows,
        columns = columns
    )


fun DashboardWithCells.toEntity(): DashboardEntity = DashboardEntity(id, name, rows, columns)
fun Cell.toEntity(dashboardId: Int): CellEntity =
    CellEntity(dashboardId, row, column, pictogram?.url, pictogram?.keyword)