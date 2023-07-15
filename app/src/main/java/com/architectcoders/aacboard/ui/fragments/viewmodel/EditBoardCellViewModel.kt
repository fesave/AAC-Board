package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.use_case.cell.get.GetCellUseCase
import com.architectcoders.aacboard.domain.use_case.cell.save.SaveCellUseCase
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.model.toUIPictogram
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditBoardCellViewModel(
    private val dashBoardId: Int,
    private val row: Int,
    private val column: Int,
    private val saveCellUseCase: SaveCellUseCase,
    private val getCellUseCase: GetCellUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<EditBoardCellUiState> = MutableStateFlow(
        EditBoardCellUiState(),
    )
    val state: StateFlow<EditBoardCellUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val cell: Cell? = getCellUseCase(dashBoardId, row, column)
            updateUiState(
                _state.value.copy(
                    row = row,
                    column = column,
                    pictogram = cell?.cellPictogram?.toUIPictogram()
                )
            )
        }
    }

    fun getColumn() = column
    fun getRow() = row

    fun onSaveClicked(keyword: String) {
        _state.value.apply {
            pictogram?.let {
                viewModelScope.launch {
                    saveCellUseCase(
                        dashBoardId,
                        Cell(row, column, CellPictogram(keyword, it.url))
                    )
                    updateUiState(_state.value.copy(exit = true))
                }
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