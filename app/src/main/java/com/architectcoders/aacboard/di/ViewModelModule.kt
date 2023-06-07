package com.architectcoders.aacboard.di

import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.MainDashboardViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.SearchPictogramsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListDashboardsViewModel(get(), get(), get(), get(), get()) }
    viewModel { MainDashboardViewModel(get()) }
    viewModel { SearchPictogramsViewModel(get()) }
}