package com.architectcoders.aacboard.model.datasource

import com.architectcoders.aacboard.model.database.DashboardDao


class DashboardLocalDataSource(private val dashboardDao: DashboardDao) {
    val dashboards = dashboardDao.getDashboards()
    fun getDashBoardWithCells(id: Int) = dashboardDao.getDashboard(id)
}

