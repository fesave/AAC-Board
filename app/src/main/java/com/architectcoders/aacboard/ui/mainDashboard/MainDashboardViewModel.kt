package com.architectcoders.aacboard.ui.mainDashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainDashboardViewModel(
    getPreferredDashboardIdUseCase: GetPreferredDashboardIdUseCase,
    getDashboardUseCase: GetDashboardUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<MainDashboardUiState> = MutableStateFlow(
        MainDashboardUiState()
    )
    val state: StateFlow<MainDashboardUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPreferredDashboardIdUseCase().collect { id ->
                val dashboard = getDashboardUseCase(id)
                updateUiState(_state.value.copy(dashboard = dashboard, loading = false))
            }
        }
    }

    fun onPictogramClicked(pictogram: Pictogram?) {
        pictogram?.let {
            val pictogramList = _state.value.selectedPictograms + pictogram
            updateUiState(_state.value.copy(selectedPictograms = pictogramList))
        }
    }

    fun onClearSelectionClicked() {
        updateUiState(_state.value.copy(selectedPictograms = emptyList()))
    }

    private fun updateUiState(newUiState: MainDashboardUiState) {
        _state.update { newUiState }
    }

    fun onClearLastSelectionClicked() {
        val selection = _state.value.selectedPictograms.dropLast(1)
        updateUiState(_state.value.copy(selectedPictograms = selection))
    }
}

@Suppress("UNCHECKED_CAST")
class MainDashboardViewModelFactory(
    private val getPreferredDashboardIdUseCase: GetPreferredDashboardIdUseCase,
    private val getDashboardUseCase: GetDashboardUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainDashboardViewModel(
            getPreferredDashboardIdUseCase,
            getDashboardUseCase
        ) as T
    }
}