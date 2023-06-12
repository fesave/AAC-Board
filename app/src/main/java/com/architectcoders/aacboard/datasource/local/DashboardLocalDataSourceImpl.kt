package com.architectcoders.aacboard.datasource.local

import com.architectcoders.aacboard.data.datasource.local.DashboardLocalDataSource
import com.architectcoders.aacboard.database.dao.DashboardDao
import com.architectcoders.aacboard.database.entity.*
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DashboardLocalDataSourceImpl(private val dashboardDao: DashboardDao) :
    DashboardLocalDataSource {

    override fun getDashboards(): Flow<List<Dashboard>> =
        dashboardDao.getDashboards().map { list ->
            list.map { dashboard ->
                dashboard.toDashboard()
            }
        }

    override fun getDashBoardWithCells(id: Int): Flow<DashboardWithCells?> =
        dashboardDao.getDashboard(id).map { it?.toDashboardWithCells() }

    override suspend fun saveDashboard(dashboard: DashboardWithCells) {
        dashboardDao.insertDashboard(dashboard.toDashboardEntity())
        dashboardDao.insertCells(dashboard.cells.map { it.toCellEntity(dashboard.id) })
    }

    override suspend fun deleteDashboard(id: Int) {
        dashboardDao.deleteDashboardEntity(id)
    }

    override suspend fun deleteCells(dashboardId: Int, cells: List<Cell>) {
        dashboardDao.deleteCells(cells.map { it.toCellEntity(dashboardId) })
    }

    override suspend fun deleteCellsContent(dashboardId: Int, cells: List<Cell>) {
        dashboardDao.insertCells(
            cells.map {
                it.toCellEntity(dashboardId).copy(url = null, text = null)
            },
        )
    }
}