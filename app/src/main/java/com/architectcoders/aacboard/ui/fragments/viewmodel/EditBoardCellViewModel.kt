package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.use_case.cell.get.GetCellUseCase
import com.architectcoders.aacboard.domain.use_case.cell.save.SaveCellUseCase
import com.architectcoders.aacboard.ui.fragments.EditBoardCellFragmentArgs
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.model.toUIPictogram
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditBoardCellViewModel(
    savedStateHandle: SavedStateHandle,
    private val saveCellUseCase: SaveCellUseCase,
    private val getCellUseCase: GetCellUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<EditBoardCellUiState> = MutableStateFlow(
        EditBoardCellUiState(),
    )
    val state: StateFlow<EditBoardCellUiState> get() = _state.asStateFlow()
    private val dashboardId: Int
    private val row: Int
    private val column: Int

    init {
        EditBoardCellFragmentArgs.fromSavedStateHandle(savedStateHandle).apply {
            this@EditBoardCellViewModel.dashboardId = dashboardId
            this@EditBoardCellViewModel.row = row
            this@EditBoardCellViewModel.column = column
        }
        viewModelScope.launch {
            val cell: Cell = getCellUseCase(dashboardId, row, column) ?: Cell(row, column, null)
            updateUiState(_state.value.copy(pictogram = cell.cellPictogram?.toUIPictogram()))
        }
    }

    fun onSaveClicked(keyword: String) {
        _state.value.pictogram?.let {
            viewModelScope.launch {
                saveCellUseCase(
                    dashboardId,
                    Cell(row, column, CellPictogram(keyword, it.url)),
                )
                updateUiState(_state.value.copy(exit = true))
            }
        }
    }

    fun onUpdatePictogram(newPictogram: PictogramUI?) {
        newPictogram?.let {
            updateUiState(_state.value.copy(pictogram = newPictogram))
        }
    }

    private fun updateUiState(newUiState: EditBoardCellUiState) {
        _state.update { newUiState }
    }

    data class EditBoardCellUiState(
        val row: Int = 1,
        val column: Int = 1,
        val pictogram: PictogramUI? = null,
        val exit: Boolean = false,
    )
}