package com.architectcoders.aacboard.ui.mainDashboard

import com.architectcoders.aacboard.domain.DashboardWithCells
import com.architectcoders.aacboard.domain.Pictogram

data class MainDashboardUiState(
    val dashboard: DashboardWithCells? = null,
    val loading: Boolean = true,
    val error: String? = null,
    val selectedPictograms: List<Pictogram> = emptyList()
)