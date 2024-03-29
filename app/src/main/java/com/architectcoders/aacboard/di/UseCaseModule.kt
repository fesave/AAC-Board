package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.domain.use_case.cell.delete.DeleteCellUseCase
import com.architectcoders.aacboard.domain.use_case.cell.generate.GenerateDashBoardCellsUseCase
import com.architectcoders.aacboard.domain.use_case.cell.get.GetCellUseCase
import com.architectcoders.aacboard.domain.use_case.cell.save.SaveCellUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.delete.DeleteDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetAllDashboardsUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetMainDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetPreferredDashboardIdUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SaveDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SetPreferredDashboardIdUseCase
import com.architectcoders.aacboard.domain.use_case.location.GetUserLanguageUseCase
import com.architectcoders.aacboard.domain.use_case.search.SearchPictogramsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { DeleteDashboardUseCase(get()) }
    factory { GetAllDashboardsUseCase(get()) }
    factory { GetMainDashboardUseCase(get()) }
    factory { GetDashboardUseCase(get()) }
    factory { GetPreferredDashboardIdUseCase(get()) }
    factory { SaveDashboardUseCase(get()) }
    factory { SetPreferredDashboardIdUseCase(get()) }
    factory { GetCellUseCase(get()) }
    factory { SaveCellUseCase(get()) }
    factory { DeleteCellUseCase(get()) }
    factory { SearchPictogramsUseCase(get()) }
    factory { GetUserLanguageUseCase(get()) }
    factory { GenerateDashBoardCellsUseCase() }
}