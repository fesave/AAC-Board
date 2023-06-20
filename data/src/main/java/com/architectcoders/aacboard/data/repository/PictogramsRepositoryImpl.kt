package com.architectcoders.aacboard.data.repository

import com.architectcoders.aacboard.data.datasource.local.DashboardLocalDataSource
import com.architectcoders.aacboard.data.datasource.local.DeviceDataSource
import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class PictogramsRepositoryImpl(
    private val deviceDataSource: DeviceDataSource,
    private val localDataSource: DashboardLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : PictogramsRepository {

    override suspend fun getDashboards(): Flow<List<Dashboard>> = localDataSource.getDashboards()

    override fun getDashBoardWithCells(id: Int): Flow<DashboardWithCells?> =
        localDataSource.getDashBoardWithCells(id)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMainDashboard(): Flow<DashboardWithCells?> =
        deviceDataSource.getPreferredDashboardId()
            .distinctUntilChanged()
            .flatMapLatest {
                localDataSource.getDashBoardWithCells(it)
            }

    override suspend fun saveDashboard(dashboard: DashboardWithCells) {
        localDataSource.saveDashboard(dashboard)
    }

    override suspend fun deleteDashboard(id: Int) {
        localDataSource.deleteDashboard(id)
    }

    override suspend fun getDashboardCell(dashboardId: Int, row: Int, column: Int): Cell? {
        return localDataSource.getDashboardCell(dashboardId, row, column)
    }

    override suspend fun saveDashboardCell(dashboardId: Int, cell: Cell) {
        localDataSource.saveDashboardCell(dashboardId, cell)
    }

    override suspend fun deleteDashboardCell(dashboardId: Int, cell: Cell) {
        localDataSource.deleteDashboardCell(dashboardId, cell)
    }

    override suspend fun deleteDashboardCells(dashboardId: Int, cells: List<Cell>) {
        localDataSource.deleteCells(dashboardId, cells)
    }

    override suspend fun deleteDashboardCellContent(dashboardId: Int, cells: List<Cell>) {
        localDataSource.deleteCellsContent(dashboardId, cells)
    }

    override fun getPreferredDashboardId(): Flow<Int> = deviceDataSource.getPreferredDashboardId()

    override suspend fun setPreferredDashboardId(id: Int) =
        deviceDataSource.setPreferredDashboardId(id)

    override suspend fun searchPictograms(language: String, searchString: String): Response<List<CellPictogram>> {
        return remoteDataSource.searchPictos(language, searchString)
    }
}