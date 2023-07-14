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

    val dashBoardId = (0..Integer.MAX_VALUE).random()

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
            saveDashboardUseCase.invoke(newDashBoard)
        }
        _state.update { _state.value.copy(navigateToDetail = true) }
    }

    fun onSaveButtonClicked(name: String, columns: String, rows: String) {
        if (name.isEmpty() || rows.isEmpty() || columns.isEmpty()) {
            _state.update { _state.value.copy(showError = true) }
        } else {
            val newDashBoard = DashboardWithCells(
                id = dashBoardId,
                name = name,
                rows = rows.toInt(),
                columns = columns.toInt(),
                cells = generateCells(0, rows.toInt(), columns.toInt()),
            )
            saveNewDashBoard(newDashBoard)
        }
        _state.update { _state.value.copy(showError = false) }
    }

    private fun generateCells(startingId: Int, rows: Int, columns: Int): List<Cell> {
        return generateDashBoardCellsUseCase(startingId, rows, columns)
    }

    data class NewDashBoardUiState(
        val pictogram: PictogramUI? = null,
        val isLoading: Boolean = false,
        val showError: Boolean = false,
        val navigateToDetail: Boolean = false,
    )
}