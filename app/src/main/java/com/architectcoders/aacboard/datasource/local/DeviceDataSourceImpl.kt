package com.architectcoders.aacboard.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.architectcoders.aacboard.data.datasource.local.DeviceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class DeviceDataSourceImpl(private val context: Context) : DeviceDataSource {

    override fun getPreferredDashboardId(): Flow<Int> {
        return context.userPreferences.data.map { preferences -> preferences[DASHBOARD_ID] ?: -1 }
    }

    override suspend fun setPreferredDashboardId(id: Int) {
        context.userPreferences.edit { preferences ->
            preferences[DASHBOARD_ID] = id
        }
    }

    companion object {
        val DASHBOARD_ID = intPreferencesKey("dashboard_id")
    }
}