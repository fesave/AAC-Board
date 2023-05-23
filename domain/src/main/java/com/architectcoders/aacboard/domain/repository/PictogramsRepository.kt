package com.architectcoders.aacboard.domain.repository

import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import kotlinx.coroutines.flow.Flow

interface PictogramsRepository {

    suspend fun getDashboards(): Flow<List<Dashboard>>

    suspend fun getDashBoardWithCells(id: Int): DashboardWithCells?

    suspend fun saveDashboard(dashboard: DashboardWithCells)

    suspend fun deleteDashboard(id: Int)

    suspend fun deleteDashboardCells(dashboardId: Int, cells: List<Cell>)

    suspend fun deleteDashboardCellContent(dashboardId: Int, cells: List<Cell>)

    suspend fun setPreferredDashboardId(id: Int)

    suspend fun searchPictograms(searchString: String): List<CellPictogram>

    fun getPreferredDashboardId(): Flow<Int>
}