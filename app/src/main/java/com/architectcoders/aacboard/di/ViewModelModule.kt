package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.MainDashboardViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.SearchPictogramsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListDashboardsViewModel(get(), get(), get(), get(), get()) }
    viewModel { MainDashboardViewModel(get()) }
    viewModel { SearchPictogramsViewModel(get(), get()) }
    viewModel { parameters -> EditBoardCellViewModel(
                                parameters.get(0),
                                parameters.get(1),
                                parameters.get(2),
                                get(),get()) }
}