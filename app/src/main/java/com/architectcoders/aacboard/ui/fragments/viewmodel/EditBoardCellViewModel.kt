package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.data.PictogramUI
import com.architectcoders.aacboard.data.toUIPictogram
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.use_case.cell.get.GetCellUseCase
import com.architectcoders.aacboard.domain.use_case.cell.save.SaveCellUseCase
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel.Destination.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditBoardCellViewModel(
    private val saveCellUseCase: SaveCellUseCase,
    private val getCellUseCase: GetCellUseCase
) : ViewModel() {

    private val dashboardId: Int
    private val row: Int
    private val column: Int

    private val _state: MutableStateFlow<EditBoardCellUiState> = MutableStateFlow(
        EditBoardCellUiState(),
    )
    val state: StateFlow<EditBoardCellUiState> get() = _state.asStateFlow()

    init {
        dashboardId=1
        row=1
        column=2
        viewModelScope.launch {
            val cell: Cell? = getCellUseCase(dashboardId, row, column) ?: Cell(row, column, null)
            updateUiState(_state.value.copy(pictogram = cell?.cellPictogram?.toUIPictogram()))
        }
    }

    fun onSaveClicked(keyword: String) {
        _state.value.pictogram?.let {
            viewModelScope.launch {
                saveCellUseCase(
                    dashboardId,
                    Cell(row, column, CellPictogram(keyword, it.url))
                )
                updateUiState(_state.value.copy(destination = BACK))
            }
        }
    }

    fun onPictogramClicked() {
        updateUiState(_state.value.copy(destination = SEARCH_PICTOGRAM))
    }

    fun onUpdatePictogram(newPictogram: PictogramUI?) {
        newPictogram?.let {
            updateUiState(_state.value.copy(pictogram = newPictogram))
        }
    }

    private fun updateUiState(newUiState: EditBoardCellUiState) {
        _state.update { newUiState }
    }

    fun resetDestination() {
        updateUiState(_state.value.copy(destination = null))
    }

    data class EditBoardCellUiState(
        val row: Int = 1,
        val column: Int = 1,
        val pictogram: PictogramUI?= null,
        val destination: Destination?= null
    )

    enum class Destination {BACK, SEARCH_PICTOGRAM}

}