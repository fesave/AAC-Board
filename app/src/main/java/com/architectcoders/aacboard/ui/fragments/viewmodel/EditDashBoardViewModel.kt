package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.use_case.dashboard.delete.DeleteDashboardUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetDashboardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditDashBoardViewModel(
    private val dashBoardId: Int,
    private val getDashboardUseCase: GetDashboardUseCase,
    private val deleteDashboardUseCase: DeleteDashboardUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(EditDashBoardUiState())
    val state: StateFlow<EditDashBoardUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(isLoading = true) }
            getDashboardUseCase(dashBoardId).collect() { dashBoarWithCells ->
                _state.update {
                    _state.value.copy(
                        dashboardsWithCells = dashBoarWithCells,
                        isLoading = false,
                    )
                }
            }
        }
    }

    data class EditDashBoardUiState(
        val isLoading: Boolean = true,
        val dashboardsWithCells: DashboardWithCells? = null,
    )

    fun onDeleteButtonClicked() {
        viewModelScope.launch {
            deleteDashboardUseCase.invoke(dashBoardId)
        }
    }
}