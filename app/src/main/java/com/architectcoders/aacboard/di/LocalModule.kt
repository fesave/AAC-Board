package com.architectcoders.aacboard.di

import androidx.room.Room
import com.architectcoders.aacboard.data.datasource.local.DashboardLocalDataSource
import com.architectcoders.aacboard.data.datasource.local.DeviceDataSource
import com.architectcoders.aacboard.database.DashboardDatabase
import com.architectcoders.aacboard.datasource.local.DashboardLocalDataSourceImpl
import com.architectcoders.aacboard.datasource.local.DeviceDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            DashboardDatabase::class.java,
            "dashboard-db",
        ).build()
    }

    single { get<DashboardDatabase>().dashboardDao() }

    factory<DashboardLocalDataSource> { DashboardLocalDataSourceImpl(get()) }
    factory<DeviceDataSource> { DeviceDataSourceImpl(androidContext()) }
}