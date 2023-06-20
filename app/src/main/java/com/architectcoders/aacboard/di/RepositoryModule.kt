package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.data.repository.PictogramsRepositoryImpl
import com.architectcoders.aacboard.data.repository.RegionRepositoryImpl
import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import com.architectcoders.aacboard.domain.repository.RegionRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<PictogramsRepository> { PictogramsRepositoryImpl(get(), get(), get()) }
    factory<RegionRepository> { RegionRepositoryImpl(get(), get()) }
}