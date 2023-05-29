package com.architectcoders.aacboard.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.architectcoders.aacboard.data.datasource.local.DeviceDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class DeviceDataSourceImpl(private val context: Context) : DeviceDataSource {

    private val geocoder: Geocoder by lazy {
        Geocoder(context)
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    override fun getPreferredDashboardId(): Flow<Int> {
        return context.userPreferences.data.map { preferences -> preferences[DASHBOARD_ID] ?: -1 }
    }

    override suspend fun setPreferredDashboardId(id: Int) {
        context.userPreferences.edit { preferences ->
            preferences[DASHBOARD_ID] = id
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastUserRegion(): String? =
        suspendCancellableCoroutine { cancellableContinuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    cancellableContinuation.resume(it.result.toRegion())
                }
        }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }

    companion object {
        val DASHBOARD_ID = intPreferencesKey("dashboard_id")
    }
}