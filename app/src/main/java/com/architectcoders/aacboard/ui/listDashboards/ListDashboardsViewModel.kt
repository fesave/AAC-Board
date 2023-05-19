package com.architectcoders.aacboard.ui.listDashboards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.ui.utils.dashboardOne
import com.architectcoders.aacboard.ui.utils.dashboardThree
import com.architectcoders.aacboard.ui.utils.dashboardTwo
import com.architectcoders.aacboard.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListDashboardsViewModel(
    private val getAllDashboardsUseCase: GetAllDashboardsUseCase,
    private val setPreferredDashboardIdUseCase: SetPreferredDashboardIdUseCase,
    private val getPreferredDashboardIdUseCase: GetPreferredDashboardIdUseCase,
    private val deleteDashboardUseCase: DeleteDashboardUseCase,
    private val saveDashboardUseCase: SaveDashboardUseCase

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
                preferredDashboardId = id
            )
        }
    }

    private fun onAllDashboardsCollected(dashboards: List<Dashboard>) {
        _state.update {
            _state.value.copy(
                loading = false,
                dashboards = dashboards.map { dashboard ->
                    dashboard.toDashBoardUiItem(it.preferredDashboardId)
                })
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
}

fun List<DashboardUiItem>.updatePreferred(preferredId: Int) =
    map { it.copy(isPreferred = it.id == preferredId) }


fun Dashboard.toDashBoardUiItem(preferredId: Int) = DashboardUiItem(id, name, id == preferredId)


@Suppress("UNCHECKED_CAST")
class ListDashboardsViewModelFactory(
    private val getAllDashboardsUseCase: GetAllDashboardsUseCase,
    private val setPreferredDashboardIdUseCase: SetPreferredDashboardIdUseCase,
    private val getPreferredDashboardIdUseCase: GetPreferredDashboardIdUseCase,
    private val deleteDashboardUseCase: DeleteDashboardUseCase,
    private val safeDashboardUseCase: SaveDashboardUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListDashboardsViewModel(
            getAllDashboardsUseCase,
            setPreferredDashboardIdUseCase,
            getPreferredDashboardIdUseCase,
            deleteDashboardUseCase,
            safeDashboardUseCase
        ) as T
    }
}