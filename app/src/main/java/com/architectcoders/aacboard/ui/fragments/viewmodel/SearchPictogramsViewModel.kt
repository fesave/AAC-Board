package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.data.Error
import com.architectcoders.aacboard.domain.data.Response.Failure
import com.architectcoders.aacboard.domain.data.Response.Success
import com.architectcoders.aacboard.domain.use_case.location.GetUserLanguageUseCase
import com.architectcoders.aacboard.domain.use_case.search.SearchPictogramsUseCase
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.model.toUIPictogram
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchPictogramsViewModel(
    private val searchPictogramsUseCase: SearchPictogramsUseCase,
    private val getUserLanguageUseCase: GetUserLanguageUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SearchPictogramUiState> = MutableStateFlow(
        SearchPictogramUiState(),
    )
    val state: StateFlow<SearchPictogramUiState> get() = _state.asStateFlow()

    fun onSearchClicked(searchString: String) {
        if (searchString.isEmpty()) return
        updateUiState(
            _state.value.copy(
                searchString = searchString,
                loading = true,
                foundPictograms = emptyList()
            )
        )
        viewModelScope.launch {
            val response = searchPictogramsUseCase(getUserLanguageUseCase(), searchString)
            when (response) {
                is Success -> updateUiState(
                    _state.value.copy(
                        foundPictograms = response.result.map { it.toUIPictogram() },
                        loading = false
                    )
                )
                is Failure -> updateUiState(
                    _state.value.copy(
                        error = response.error,
                        loading = false
                    )
                )
            }
        }
    }

    fun onPictogramClicked(uiPictogram: PictogramUI?) {
        uiPictogram?.let {
            updateUiState(
                _state.value.copy(
                    selectedPictogram = uiPictogram.copy(keyword = _state.value.searchString)
                )
            )
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
        val foundPictograms: List<PictogramUI> = emptyList(),
        val selectedPictogram: PictogramUI? = null
    )
}