package com.architectcoders.aacboard.data.datasource.local

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import kotlinx.coroutines.flow.Flow

interface DashboardLocalDataSource {

    fun getDashboards(): Flow<List<Dashboard>>

    fun getDashBoardWithCells(id: Int): Flow<DashboardWithCells?>

    suspend fun saveDashboard(dashboard: DashboardWithCells)

    suspend fun deleteDashboard(id: Int)

    suspend fun getDashboardCell(dashboardId: Int, row: Int, column: Int): Cell?

    suspend fun saveDashboardCell(dashboardId: Int, cell: Cell)

    suspend fun deleteDashboardCell(dashboardId: Int, cell: Cell)

    suspend fun deleteCells(dashboardId: Int, cells: List<Cell>)

    suspend fun deleteCellsContent(dashboardId: Int, cells: List<Cell>)
}