package com.architectcoders.aacboard.data.repository

import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker
import com.architectcoders.aacboard.data.datasource.local.LocationDataSource
import com.architectcoders.aacboard.domain.repository.RegionRepository

class RegionRepositoryImpl(
    private val locationDataSource: LocationDataSource,
    private val appPermissionChecker: AppPermissionChecker
) : RegionRepository {

    override suspend fun getUserLanguage(): String {
        return if (appPermissionChecker.check(AppPermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.getUserLanguage()?.lowercase() ?: DEFAULT_USER_LANGUAGE
        } else {
            DEFAULT_USER_LANGUAGE
        }
    }

    companion object {
        private const val DEFAULT_USER_LANGUAGE = "en"
    }
}