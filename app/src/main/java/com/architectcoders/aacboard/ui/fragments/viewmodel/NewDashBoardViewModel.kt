package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SaveDashboardUseCase
import com.architectcoders.aacboard.ui.model.PictogramUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewDashBoardViewModel(
    private val saveDashboardUseCase: SaveDashboardUseCase,
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

    fun onEditButtonClicked(newDashBoard: DashboardWithCells) {
        viewModelScope.launch {
            saveDashboardUseCase.invoke(newDashBoard)
        }
    }

    fun generateCells(startingId: Int, rows: Int, columns: Int): List<Cell> {
        val cells = mutableListOf<Cell>()
        var id = startingId
        (0 until rows).forEachIndexed { rowIndex, _ ->
            (0 until columns).forEachIndexed { columnIndex, _ ->
                cells.add(
                    Cell(
                        rowIndex,
                        columnIndex,
                        CellPictogram(
                            "",
                            "",
                        ),
                    ),
                )
                id++
            }
        }
        return cells.toList()
    }

    data class NewDashBoardUiState(
        val pictogram: PictogramUI? = null,
        val isLoading: Boolean = false,
    )
}