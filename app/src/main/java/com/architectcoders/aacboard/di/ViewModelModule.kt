package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditDashBoardViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.MainDashboardViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.SearchPictogramsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListDashboardsViewModel(get(), get(), get()) }
    viewModel { MainDashboardViewModel(get()) }
    viewModel { SearchPictogramsViewModel(get(), get()) }
    viewModel { params->
        EditBoardCellViewModel(
            dashBoardId = params.component1(),
            row = params.component2(),
            column = params.component3(),
            get(),
            get(),
        )
    }
    viewModel { NewDashBoardViewModel(get(), get()) }
    viewModel { params -> EditDashBoardViewModel(dashBoardId = params.get(), get(), get()) }
}