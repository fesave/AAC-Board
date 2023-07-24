package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.domain.use_case.cell.generate.GenerateDashBoardCellsUseCase
import com.architectcoders.aacboard.domain.use_case.dashboard.save.SaveDashboardUseCase
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel.NewDashBoardUiState.Field
import com.architectcoders.aacboard.ui.model.PictogramUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@SuppressWarnings("TooManyFunctions")
class NewDashBoardViewModel(
    private val saveDashboardUseCase: SaveDashboardUseCase,
    private val generateDashBoardCellsUseCase: GenerateDashBoardCellsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(NewDashBoardUiState())
    val state get() = _state.asStateFlow()

    fun onUpdatePictogram(newPictogram: PictogramUI?) {
        viewModelScope.launch {
            _state.update { state -> state.copy(isLoading = true) }
            newPictogram?.let {
                clearFilteredErrorsFor(Field.PICTOGRAM)
                _state.update { state -> state.copy(pictogram = newPictogram) }
            }
            _state.update { state -> state.copy(isLoading = false) }
        }
    }

    fun clearNavigation() {
        _state.update { state -> state.copy(navigateToDashboardId = null) }
    }

    fun onInputFieldChanged(inputText: String, field: Field) {
        clearFilteredErrorsFor(field)
        when (field) {
            Field.NAME -> onNameValueChanged(inputText)
            Field.COLUMNS -> onColumnValueChanged(inputText)
            Field.ROWS -> onRowsValueChanged(inputText)
            else -> Unit
        }
    }

    private fun clearFilteredErrorsFor(field: Field) {
        val filteredList = _state.value.inputFieldErrors.toMutableSet().apply { remove(field) }
        _state.update { state -> state.copy(inputFieldErrors = filteredList) }
    }

    private fun onColumnValueChanged(columns: String) {
        _state.update { state -> state.copy(columns = columns.toIntOrNull() ?: 0) }
    }

    private fun onRowsValueChanged(rows: String) {
        _state.update { state -> state.copy(rows = rows.toIntOrNull() ?: 0) }
    }

    private fun onNameValueChanged(name: String) {
        _state.update { state -> state.copy(name = name) }
    }

    private fun saveNewDashBoard(newDashBoard: DashboardWithCells) {
        viewModelScope.launch {
            val newDashboardId = saveDashboardUseCase(newDashBoard)
            _state.update { state ->
                state.copy(
                    dashboardId = newDashboardId,
                    navigateToDashboardId = newDashboardId
                )
            }
        }
    }

    fun onSaveButtonClicked() {
        getInputErrorFields().let { errors ->
            if (errors.isEmpty()) {
                saveNewDashBoard(newDashBoard = buildNewDashboardInfo())
            } else {
                _state.update { state -> state.copy(inputFieldErrors = errors) }
            }
        }
    }

    private fun getInputErrorFields(): Set<Field> {
        val inputErrors = _state.value.inputFieldErrors.toMutableSet()
        if (_state.value.name.isEmpty()) inputErrors.add(Field.NAME)
        if (_state.value.columns == 0) inputErrors.add(Field.COLUMNS)
        if (_state.value.rows == 0) inputErrors.add(Field.ROWS)
        if (_state.value.pictogram?.url == null) inputErrors.add(Field.PICTOGRAM)
        return inputErrors
    }

    private fun buildNewDashboardInfo() = with(_state.value) {
        DashboardWithCells(
            id = dashboardId,
            name = name,
            rows = rows,
            columns = columns,
            image = pictogram?.url ?: "",
            cells = generateDashBoardCellsUseCase(rows, columns),
        )
    }

    data class NewDashBoardUiState(
        val dashboardId: Int = 0,
        val name: String = "",
        val rows: Int = 0,
        val columns: Int = 0,
        val pictogram: PictogramUI? = null,
        val isLoading: Boolean = false,
        val inputFieldErrors: Set<Field> = setOf(),
        val navigateToDashboardId: Int? = null,
    ) {
        enum class Field {
            COLUMNS, ROWS, NAME, PICTOGRAM
        }
    }
}