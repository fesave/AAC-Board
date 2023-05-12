package com.architectcoders.aacboard.ui.mainDashboard

import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.domain.Pictogram

data class MainDashboardUiState(
    val dashboard: Dashboard? = null,
    val loading: Boolean = true,
    val error: String? = null,
    val selectedPictograms: List<Pictogram> = emptyList()
)