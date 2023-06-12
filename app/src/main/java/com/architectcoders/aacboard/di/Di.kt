package com.architectcoders.aacboard.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun Application.initDi() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDi)
        modules(
            remoteModule,
            localModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
            commonModule,
        )
    }
}