package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.use_case.dashboard.get.GetMainDashboardUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainDashboardViewModel(
    getMainDashboardUseCase: GetMainDashboardUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<MainDashboardUiState> = MutableStateFlow(
        MainDashboardUiState(),
    )
    val state: StateFlow<MainDashboardUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getMainDashboardUseCase().collect { dashboard ->
                updateUiState(_state.value.copy(dashboard = dashboard, loading = false))
            }
        }
    }

    fun onPictogramClicked(cellPictogram: CellPictogram?) {
        cellPictogram?.let {
            val pictogramList = _state.value.selectedCellPictograms + cellPictogram
            updateUiState(_state.value.copy(selectedCellPictograms = pictogramList))
        }
    }

    fun onStartSpeaking() {
        updateUiState(_state.value.copy(isSpeaking = true))
    }

    fun onFinishedSpeaking() {
        updateUiState(_state.value.copy(isSpeaking = false))
    }

    fun onClearSelectionClicked() {
        updateUiState(_state.value.copy(selectedCellPictograms = emptyList()))
    }

    fun onClearLastSelectionClicked() {
        val selection = _state.value.selectedCellPictograms.dropLast(1)
        updateUiState(_state.value.copy(selectedCellPictograms = selection))
    }

    private fun updateUiState(newUiState: MainDashboardUiState) {
        _state.update { newUiState }
    }

    data class MainDashboardUiState(
        val dashboard: DashboardWithCells? = null,
        val loading: Boolean = true,
        val selectedCellPictograms: List<CellPictogram> = emptyList(),
        val isSpeaking: Boolean = false
    )
}