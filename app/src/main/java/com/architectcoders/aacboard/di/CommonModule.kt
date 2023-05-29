package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.data.repository.PermissionChecker
import com.architectcoders.aacboard.datasource.PermissionCheckerImpl
import org.koin.dsl.module

val commonModule = module {
    factory<PermissionChecker> { PermissionCheckerImpl(get()) }
}