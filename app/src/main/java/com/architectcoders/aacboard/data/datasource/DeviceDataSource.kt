package com.architectcoders.aacboard.data.datasource

import kotlinx.coroutines.flow.Flow

interface DeviceDataSource {

    fun getPreferredDashboardId(): Flow<Int>

    suspend fun setPreferredDashboardId(id: Int)

}
