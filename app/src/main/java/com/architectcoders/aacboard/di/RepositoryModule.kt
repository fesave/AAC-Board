package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.data.repository.PictogramsRepositoryImpl
import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<PictogramsRepository> { PictogramsRepositoryImpl(get(), get(), get()) }
}