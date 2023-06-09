package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.Error
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.Response.Success
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.use_case.search.SearchPictogramsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchPictogramsViewModel(
    val searchPictogramsUseCase: SearchPictogramsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SearchPictogramUiState> = MutableStateFlow(
        SearchPictogramUiState(),
    )
    val state: StateFlow<SearchPictogramUiState> get() = _state.asStateFlow()

    fun onSearchClicked(searchString: String) {
        if (searchString.isEmpty()) return
        updateUiState(_state.value.copy(
            searchString=searchString,
            loading=true,
            foundPictograms = emptyList()))
        viewModelScope.launch {

            val response = searchPictogramsUseCase(searchString)
            when (response) {
                is Success -> updateUiState(_state.value.copy(
                    foundPictograms = response.result,
                    error=null,
                    loading = false))
                is Response.Failure -> updateUiState(_state.value.copy(
                    error = response.error,
                    loading = false))
            }
        }
    }

    fun onPictogramClicked(cellPictogram: CellPictogram?) {
        cellPictogram?.let {
            updateUiState(_state.value.copy(selectedPictogram = cellPictogram))
        }
    }

    private fun updateUiState(newUiState: SearchPictogramUiState) {
        _state.update { newUiState }
    }

    fun resetError() {
        updateUiState(_state.value.copy(error = null))
    }

    data class SearchPictogramUiState(
        val searchString: String = String(),
        val loading: Boolean = false,
        val error: Error? = null,
        val foundPictograms: List<CellPictogram> = emptyList(),
        val selectedPictogram: CellPictogram?= null
    )
}