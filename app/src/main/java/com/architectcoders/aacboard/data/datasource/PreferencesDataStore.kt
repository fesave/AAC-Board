package com.architectcoders.aacboard.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class PreferencesDataStore(private val context: Context) : DeviceDataSource {

    companion object {
        val DASHBOARD_ID = intPreferencesKey("dashboard_id")
    }

    override fun getPreferredDashboardId(): Flow<Int> {
        return context.userPreferences.data.map { preferences -> preferences[DASHBOARD_ID] ?: -1 }
    }

    override suspend fun setPreferredDashboardId(id: Int) {
        context.userPreferences.edit { preferences ->
            preferences[DASHBOARD_ID] = id
        }
    }
}
