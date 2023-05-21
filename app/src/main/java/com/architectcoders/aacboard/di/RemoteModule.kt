package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.datasource.remote.RemoteDataSourceImpl
import com.architectcoders.aacboard.network.ArasaacNetworkInstance.createArasaacNetworkInstance
import org.koin.dsl.module

val remoteModule = module {
    single { createArasaacNetworkInstance() }
    factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
}