package com.architectcoders.aacboard.model

import com.architectcoders.aacboard.App
import com.architectcoders.aacboard.data.datasource.DeviceDataSource
import com.architectcoders.aacboard.data.datasource.PreferencesDataStore
import com.architectcoders.aacboard.domain.Cell
import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.domain.DashboardWithCells
import com.architectcoders.aacboard.model.database.DashboardEntityWithCellEntities
import com.architectcoders.aacboard.model.datasource.DashboardLocalDataSource
import kotlinx.coroutines.flow.Flow
import com.architectcoders.aacboard.domain.Pictogram
import kotlinx.coroutines.flow.map
import java.util.*

class PictogramsRepository(
    application: App,
    private val deviceDataSource: DeviceDataSource = PreferencesDataStore(application),
    private val localDataSource: DashboardLocalDataSource = DashboardLocalDataSource(application.db.dashboardDao())
) {

    val dashboards = localDataSource.dashboards

    suspend fun getDashBoardWithCells(id: Int): DashboardWithCells? =
        localDataSource.getDashBoardWithCells(id)

    suspend fun saveDashboard(dashboard: DashboardWithCells) {
        localDataSource.saveDashboard(dashboard)
    }

    suspend fun deleteDashboard(id: Int) {
        localDataSource.deleteDashboard(id)
    }

    suspend fun deleteDashboardCells(dashboardId: Int, cells: List<Cell>) {
        localDataSource.deleteCells(dashboardId, cells)
    }

    suspend fun deleteDashboardCellContent(dashboardId: Int, cells: List<Cell>) {
        localDataSource.deleteCellsContent(dashboardId, cells)
    }

    fun getPreferredDashboardId(): Flow<Int> = deviceDataSource.getPreferredDashboardId()

    suspend fun setPreferredDashboardId(id: Int) = deviceDataSource.setPreferredDashboardId(id)

    suspend fun searchPictograms(searchString: String): List<Pictogram> {
        val locale = Locale.getDefault().language
        val arasaacPictograms = RemoteConnection.service.searchPictos(locale, searchString)
        return arasaacPictograms.map { it.toDomain() }
    }
}