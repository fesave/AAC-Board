package com.architectcoders.aacboard.ui.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.ui.model.PictogramUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewDashBoardViewModel : ViewModel() {

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

    data class NewDashBoardUiState(
        val pictogram: PictogramUI? = null,
        val isLoading: Boolean = false,
    )
}