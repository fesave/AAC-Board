package com.architectcoders.aacboard.datasource

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker

class AppPermissionCheckerImpl(
    private val application: Application,
) : AppPermissionChecker {
    override suspend fun check(permission: AppPermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId(),
        ) == PackageManager.PERMISSION_GRANTED
}

private fun AppPermissionChecker.Permission.toAndroidId() = when (this) {
    AppPermissionChecker.Permission.COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
}