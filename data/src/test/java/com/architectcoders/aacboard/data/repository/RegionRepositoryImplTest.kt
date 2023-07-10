package com.architectcoders.aacboard.data.repository

import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker
import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker.*
import com.architectcoders.aacboard.data.datasource.local.LocationDataSource
import com.architectcoders.aacboard.data.repository.RegionRepositoryImpl.Companion.DEFAULT_LANGUAGE
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryImplTest {

    @Test
    fun `Given location permission not granted, then getUserLanguage  returns default language`(): Unit =
        runTest {
            val regionRepository = buildRegionRepository(
                permissionChecker = mock {
                    on { check(Permission.COARSE_LOCATION) } doReturn false
                })
            val language = regionRepository.getUserLanguage()

            assertEquals(DEFAULT_LANGUAGE.value, language)
        }


    @Test
    fun `Given location data source returns null language, then getUserLanguage returns default language`(): Unit =
        runTest {
            val regionRepository = buildRegionRepository(
                locationDataSource = mock {
                    onBlocking { getUserLanguage() } doReturn null
                },
                permissionChecker = mock {
                    on { check(Permission.COARSE_LOCATION) } doReturn true
                })
            val language = regionRepository.getUserLanguage()

            assertEquals(DEFAULT_LANGUAGE.value, language)
        }


    @Test
    fun `When location data src returns an unsupported language, then getUserLanguage returns default`(): Unit =
        runTest {
            val unsupportedLenguage = "ast"
            val regionRepository = buildRegionRepository(
                locationDataSource = mock {
                    onBlocking { getUserLanguage() } doReturn unsupportedLenguage
                },
                permissionChecker = mock {
                    on { check(Permission.COARSE_LOCATION) } doReturn true
                })
            val language = regionRepository.getUserLanguage()

            assertEquals(DEFAULT_LANGUAGE.value, language)
        }

    @Test
    fun `When location data source returns a supported language, then getUserLanguage returns this language`(): Unit =
        runTest {
            val supportedLanguage = "es"
            val regionRepository = buildRegionRepository(
                locationDataSource = mock {
                    onBlocking { getUserLanguage() } doReturn supportedLanguage
                },
                permissionChecker = mock {
                    on { check(Permission.COARSE_LOCATION) } doReturn true
                })
            val language = regionRepository.getUserLanguage()

            assertEquals(supportedLanguage, language)
        }


    private fun buildRegionRepository(
        locationDataSource: LocationDataSource = mock(),
        permissionChecker: AppPermissionChecker = mock()
    ) = RegionRepositoryImpl(locationDataSource, permissionChecker)

}