package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.architectcoders.aacboard.databinding.FragmentNewDashboardBinding
import com.architectcoders.aacboard.ui.fragments.stateholder.NewDashBoardState
import com.architectcoders.aacboard.ui.fragments.stateholder.buildNewDashBoardCellState
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.loadUrl
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
            diff(uiState, { it.showError }, { show -> if (show) newDashBoardState.showError() })
            diff(uiState, { it.navigateToDashboardId }, { id -> id?.let(::navigateToDashboard) })
        }
    }

    private fun navigateToDashboard(id: Int) {
        newDashBoardState.onDashboardCreated(id) { viewModel.clearNavigation() }
    }

    private fun onPictogramChanged(pictogramUI: PictogramUI?) {
        pictogramUI?.url?.let { url -> binding.ivNewDashboardImage.loadUrl(url) }
    }

    private fun setOnClickListeners() {
        binding.buttonNewDashboardCancel.setOnClickListener {
            newDashBoardState.onCancel()
        }

        binding.ivNewDashboardImage.setOnClickListener {
            newDashBoardState.onSearchPictogram()
        }

        binding.buttonNewDashboardSave.setOnClickListener {
            val name = binding.textInputDashboardName.editText?.text.toString()
            val rows = binding.textInputDashboardRows.editText?.text.toString()
            val columns = binding.textInputDashboardColumns.editText?.text.toString()
            viewModel.onSaveButtonClicked(name, columns, rows)
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