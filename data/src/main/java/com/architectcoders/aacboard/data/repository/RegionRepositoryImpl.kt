package com.architectcoders.aacboard.data.repository

import com.architectcoders.aacboard.data.datasource.local.LocationDataSource
import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker
import com.architectcoders.aacboard.domain.repository.RegionRepository

class RegionRepositoryImpl(
    private val locationDataSource: LocationDataSource,
    private val appPermissionChecker: AppPermissionChecker
) : RegionRepository {

    override suspend fun getLastUserRegion(): String {
        return if (appPermissionChecker.check(AppPermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.getLastUserRegion()?.lowercase() ?: DEFAULT_USER_REGION
        } else {
            DEFAULT_USER_REGION
        }
    }

    companion object {
        private const val DEFAULT_USER_REGION = "us"
    }
}