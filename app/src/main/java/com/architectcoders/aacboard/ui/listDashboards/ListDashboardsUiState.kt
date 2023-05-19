package com.architectcoders.aacboard.ui.listDashboards

data class ListDashboardsUiState(
    val loading: Boolean = true,
    val dashboards: List<DashboardUiItem> = emptyList(),
    val preferredDashboardId: Int = -1,
    val error: String? = null
)

data class DashboardUiItem(
    val id: Int,
    val name: String,
    val isPreferred: Boolean
)