package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.domain.use_case.dashboard.delete.DeleteDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetAllDashboardsUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetPreferredDashboardIdUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SaveDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SetPreferredDashboardIdUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { DeleteDashboardUseCase(get()) }
    factory { GetAllDashboardsUseCase(get()) }
    factory { GetDashboardUseCase(get()) }
    factory { GetPreferredDashboardIdUseCase(get()) }
    factory { SaveDashboardUseCase(get()) }
    factory { SetPreferredDashboardIdUseCase(get()) }
}