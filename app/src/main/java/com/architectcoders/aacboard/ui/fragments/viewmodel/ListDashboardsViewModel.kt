package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.dashboard.Dashboard
import com.architectcoders.aacboard.domain.use_case.dashboard.delete.DeleteDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetAllDashboardsUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetPreferredDashboardIdUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SaveDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SetPreferredDashboardIdUseCase
import com.architectcoders.aacboard.ui.utils.dashboardOne
import com.architectcoders.aacboard.ui.utils.dashboardThree
import com.architectcoders.aacboard.ui.utils.dashboardTwo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListDashboardsViewModel(
    private val getAllDashboardsUseCase: GetAllDashboardsUseCase,
    private val setPreferredDashboardIdUseCase: SetPreferredDashboardIdUseCase,
    private val getPreferredDashboardIdUseCase: GetPreferredDashboardIdUseCase,
    private val deleteDashboardUseCase: DeleteDashboardUseCase,
    private val saveDashboardUseCase: SaveDashboardUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ListDashboardsUiState())
    val state: StateFlow<ListDashboardsUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPreferredDashboardIdUseCase().collect(::onPreferredDashboardCollected)
        }
        viewModelScope.launch {
            getAllDashboardsUseCase().collect(::onAllDashboardsCollected)
        }
    }

    private fun onPreferredDashboardCollected(id: Int) {
        _state.update {
            _state.value.copy(
                dashboards = _state.value.dashboards.updatePreferred(id),
                preferredDashboardId = id,
            )
        }
    }

    private fun onAllDashboardsCollected(dashboards: List<Dashboard>) {
        _state.update {
            _state.value.copy(
                loading = false,
                dashboards = dashboards.map { dashboard ->
                    dashboard.toDashBoardUiItem(it.preferredDashboardId)
                },
            )
        }
    }

    fun onPreferredDashboardClicked(id: Int) {
        viewModelScope.launch {
            setPreferredDashboardIdUseCase(id)
        }
    }

    fun onDeleteDashboard(id: Int) {
        viewModelScope.launch {
            if (_state.value.preferredDashboardId == id) {
                setPreferredDashboardIdUseCase(-1)
            }
            deleteDashboardUseCase(id)
        }
    }

    fun onCreateNewDashboardClicked() {
        viewModelScope.launch {
            saveDashboardUseCase(dashboardOne)
            saveDashboardUseCase(dashboardTwo)
            saveDashboardUseCase(dashboardThree)
        }
    }

    data class ListDashboardsUiState(
        val loading: Boolean = true,
        val dashboards: List<DashboardUiItem> = emptyList(),
        val preferredDashboardId: Int = -1,
        val error: String? = null,
    )

    data class DashboardUiItem(
        val id: Int,
        val name: String,
        val isPreferred: Boolean,
    )
}

fun List<ListDashboardsViewModel.DashboardUiItem>.updatePreferred(preferredId: Int) =
    map { it.copy(isPreferred = it.id == preferredId) }

fun Dashboard.toDashBoardUiItem(preferredId: Int) =
    ListDashboardsViewModel.DashboardUiItem(id, name, id == preferredId)