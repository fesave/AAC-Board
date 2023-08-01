package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.datasource.remote.RemoteDataSourceImpl
import com.architectcoders.aacboard.network.ArasaacNetworkInstance.createArasaacNetworkInstance
import com.architectcoders.aacboard.network.ArasaacNetworkInstance.getOkHttpClient
import org.koin.dsl.module

val remoteModule = module {
    single { getOkHttpClient() }
    single { createArasaacNetworkInstance("http://localhost:8080", get()) }
    factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
}