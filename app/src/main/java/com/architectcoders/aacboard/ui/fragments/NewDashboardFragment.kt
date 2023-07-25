package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentNewDashboardBinding
import com.architectcoders.aacboard.ui.fragments.stateholder.NewDashBoardState
import com.architectcoders.aacboard.ui.fragments.stateholder.buildNewDashBoardCellState
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel.NewDashBoardUiState.Field
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel.NewDashBoardUiState.Field.COLUMNS
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel.NewDashBoardUiState.Field.NAME
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel.NewDashBoardUiState.Field.PICTOGRAM
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel.NewDashBoardUiState.Field.ROWS
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.loadUrl
import com.architectcoders.aacboard.ui.utils.tint
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewDashboardFragment : Fragment() {

    private var _binding: FragmentNewDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewDashBoardViewModel by viewModel()

    private lateinit var newDashBoardState: NewDashBoardState

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewDashboardBinding.inflate(inflater, container, false)
        newDashBoardState = buildNewDashBoardCellState()
        initView()
        checkSearchResponse()
        return binding.root
    }

    private fun initView() {
        setOnClickListeners()
        viewModel.state.let { uiState ->
            diff(uiState, { it.isLoading }, { binding.progressCircular.isVisible = it })
            diff(uiState, { it.pictogram }, ::onPictogramChanged)
            diff(uiState, { it.navigateToDashboardId }, { id -> id?.let(::navigateToDashboard) })
            diff(uiState, { it.inputFieldErrors }, ::updateFieldErrors)
        }
    }

    private fun updateFieldErrors(errorFields: Set<Field>) {
        with(binding) {
            textInputDashboardColumns.error =
                errorFields.find { it == COLUMNS }
                    ?.let { getString(R.string.dashboard_input_columns_error) }
            textInputDashboardRows.error =
                errorFields.find { it == ROWS }
                    ?.let { getString(R.string.dashboard_input_rows_error) }
            textInputDashboardName.error =
                errorFields.find { it == NAME }
                    ?.let { getString(R.string.dashboard_input_name_error) }
            errorFields.find { it == PICTOGRAM }?.let {
                ivNewDashboardImage.tint(context, R.color.light_red)
            }
        }
    }

    private fun navigateToDashboard(id: Int) {
        newDashBoardState.onDashboardCreated(id) { viewModel.clearNavigation() }
    }

    private fun onPictogramChanged(pictogramUI: PictogramUI?) {
        pictogramUI?.url?.let { url -> binding.ivNewDashboardImage.loadUrl(url) }
    }

    private fun setOnClickListeners() {
        with(binding) {
            buttonNewDashboardCancel.setOnClickListener {
                newDashBoardState.onCancel()
            }
            ivNewDashboardImage.setOnClickListener {
                newDashBoardState.onSearchPictogram()
            }
            buttonNewDashboardSave.setOnClickListener {
                viewModel.onSaveButtonClicked()
            }
            textInputDashboardName.editText?.doAfterTextChanged { edit ->
                edit?.let { viewModel.onInputFieldChanged(it.toString(), NAME) }
            }
            textInputDashboardColumns.editText?.doAfterTextChanged { edit ->
                edit?.let { viewModel.onInputFieldChanged(it.toString(), COLUMNS) }
            }
            textInputDashboardRows.editText?.doAfterTextChanged { edit ->
                edit?.let { viewModel.onInputFieldChanged(it.toString(), ROWS) }
            }
        }
    }

    private fun checkSearchResponse() {
        newDashBoardState.checkSearchResponse {
            viewModel.onUpdatePictogram(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}