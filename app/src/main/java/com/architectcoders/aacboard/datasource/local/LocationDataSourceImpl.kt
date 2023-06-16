package com.architectcoders.aacboard.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.architectcoders.aacboard.data.datasource.local.LocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

class LocationDataSourceImpl(private val context: Context) : LocationDataSource {

    private val geocoder: Geocoder by lazy {
        Geocoder(context)
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getUserLanguage(): String? =
        suspendCancellableCoroutine { cancellableContinuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    cancellableContinuation.resume(it.result.toLanguage())
                }
        }

    private fun Location?.toLanguage(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        val countryCode = addresses?.firstOrNull()?.countryCode

        return Locale.getAvailableLocales().firstOrNull { locale ->
            locale.country == countryCode
        }?.language
    }
}