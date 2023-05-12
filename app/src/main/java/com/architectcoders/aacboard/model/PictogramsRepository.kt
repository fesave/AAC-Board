package com.architectcoders.aacboard.model

import com.architectcoders.aacboard.App
import com.architectcoders.aacboard.data.datasource.DeviceDataSource
import com.architectcoders.aacboard.data.datasource.PreferencesDataStore
import com.architectcoders.aacboard.model.database.DashboardEntityWithCellEntities
import com.architectcoders.aacboard.model.datasource.DashboardLocalDataSource
import kotlinx.coroutines.flow.Flow
import com.architectcoders.aacboard.domain.Pictogram
import java.util.*

class PictogramsRepository(
    application: App,
    private val deviceDataSource: DeviceDataSource = PreferencesDataStore(application)) {

    private val localDataSource = DashboardLocalDataSource(application.db.dashboardDao())

    val dashboards = localDataSource.dashboards

    suspend fun searchPictograms(searchString: String): List<Pictogram> {
        val locale = Locale.getDefault().language
        val arasaacPictograms = RemoteConnection.service.searchPictos(locale, searchString)
        return arasaacPictograms.map { it.toDomain() }
    }

    fun getDashBoardWithCells(id: Int): Flow<DashboardEntityWithCellEntities> =
        localDataSource.getDashBoardWithCells(id)
    
    fun getPreferredDashboardId(): Flow<Int> = deviceDataSource.getPreferredDashboardId()
    
    suspend fun setPreferredDashboardId(id: Int) = deviceDataSource.setPreferredDashboardId(id)

    
}