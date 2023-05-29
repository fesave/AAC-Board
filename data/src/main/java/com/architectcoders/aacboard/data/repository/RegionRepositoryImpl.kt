package com.architectcoders.aacboard.data.repository

import com.architectcoders.aacboard.data.datasource.local.DeviceDataSource
import com.architectcoders.aacboard.domain.repository.RegionRepository

class RegionRepositoryImpl(
    private val deviceDataSource: DeviceDataSource,
    private val permissionChecker: PermissionChecker
) : RegionRepository {

    override suspend fun getLastUserRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            deviceDataSource.getLastUserRegion()?.lowercase() ?: DEFAULT_USER_REGION
        } else {
            DEFAULT_USER_REGION
        }
    }


    companion object {
        private const val DEFAULT_USER_REGION = "us"
    }
}

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}