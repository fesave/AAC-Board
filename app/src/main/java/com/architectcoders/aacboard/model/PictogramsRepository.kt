package com.architectcoders.aacboard.model

import com.architectcoders.aacboard.App
import com.architectcoders.aacboard.model.database.DashboardEntityWithCellEntities
import com.architectcoders.aacboard.model.datasource.DashboardLocalDataSource
import kotlinx.coroutines.flow.Flow
import java.util.*

class PictogramsRepository(application: App) {

    private val localDataSource = DashboardLocalDataSource(application.db.dashboardDao())

    val dashboards= localDataSource.dashboards

    suspend fun searchPictograms(searchString: String): List<DomainPictogram> {
        val locale = Locale.getDefault().getLanguage()
        val arasaacPictograms = RemoteConnection.service.searchPictos(locale, searchString)
        return arasaacPictograms.map { it.toDomainPictogram() }
    }

    fun getDashBoardWithCells(id: Int): Flow<DashboardEntityWithCellEntities> =
        localDataSource.getDashBoardWithCells(id)

}