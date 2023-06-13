package com.architectcoders.aacboard.domain.repository

import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import kotlinx.coroutines.flow.Flow

interface PictogramsRepository {

    suspend fun getDashboards(): Flow<List<Dashboard>>

    fun getDashBoardWithCells(id: Int): Flow<DashboardWithCells?>

    suspend fun saveDashboard(dashboard: DashboardWithCells)

    suspend fun deleteDashboard(id: Int)

    suspend fun getDashboardCell(dashboardId: Int, row: Int, column:Int): Cell?

    suspend fun saveDashboardCell(dashboardId: Int, cell: Cell)

    suspend fun deleteDashboardCell(dashboardId: Int, cell: Cell)

    suspend fun deleteDashboardCells(dashboardId: Int, cells: List<Cell>)

    suspend fun deleteDashboardCellContent(dashboardId: Int, cells: List<Cell>)

    suspend fun setPreferredDashboardId(id: Int)

    suspend fun searchPictograms(language: String, searchString: String): Response<List<CellPictogram>>

    fun getPreferredDashboardId(): Flow<Int>

    fun getMainDashboard(): Flow<DashboardWithCells?>
}