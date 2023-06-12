package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.data.datasource.local.AppPermissionChecker
import com.architectcoders.aacboard.datasource.AppPermissionCheckerImpl
import org.koin.dsl.module

val commonModule = module {
    factory<AppPermissionChecker> { AppPermissionCheckerImpl(get()) }
}