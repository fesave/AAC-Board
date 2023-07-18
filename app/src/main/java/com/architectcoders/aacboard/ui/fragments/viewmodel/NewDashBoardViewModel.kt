package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.use_case.cell.generate.GenerateDashBoardCellsUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SaveDashboardUseCase
import com.architectcoders.aacboard.ui.model.PictogramUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewDashBoardViewModel(
    private val saveDashboardUseCase: SaveDashboardUseCase,
    private val generateDashBoardCellsUseCase: GenerateDashBoardCellsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NewDashBoardUiState())
    val state get() = _state.asStateFlow()

    fun onUpdatePictogram(newPictogram: PictogramUI?) {
        viewModelScope.launch {
            _state.update { _state.value.copy(isLoading = true) }
            newPictogram?.let {
                _state.update { state.value.copy(pictogram = newPictogram) }
            }
            _state.update { _state.value.copy(isLoading = false) }
        }
    }

    private fun saveNewDashBoard(newDashBoard: DashboardWithCells) {
        viewModelScope.launch {
            val newDashboardId = saveDashboardUseCase(newDashBoard)
            _state.update {
                _state.value.copy(
                    dashboardId = newDashboardId,
                    navigateToDashboardId = newDashboardId
                )
            }
        }
    }

    fun onSaveButtonClicked(name: String, columns: String, rows: String) {
        if (name.isEmpty() || rows.isEmpty() || columns.isEmpty()) {
            _state.update { _state.value.copy(showError = true) }
        } else {
            val newDashBoard = DashboardWithCells(
                id = _state.value.dashboardId,
                name = name,
                rows = rows.toInt(),
                columns = columns.toInt(),
                image = _state.value.pictogram?.url ?: "",
                cells = generateCells(rows.toInt(), columns.toInt()),
            )
            saveNewDashBoard(newDashBoard)
        }
        _state.update { _state.value.copy(showError = false) }
    }

    private fun generateCells(rows: Int, columns: Int): List<Cell> {
        return generateDashBoardCellsUseCase(rows, columns)
    }

    fun clearNavigation() {
        _state.update { _state.value.copy(navigateToDashboardId = null) }
    }

    data class NewDashBoardUiState(
        val dashboardId: Int = 0,
        val name: String = "",
        val rows: Int = 0,
        val columns: Int = 0,
        val pictogram: PictogramUI? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
        val navigateToDashboardId: Int? = null,
    )
}