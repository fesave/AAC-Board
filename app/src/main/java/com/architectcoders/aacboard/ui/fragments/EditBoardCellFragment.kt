package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.databinding.FragmentEditBoardCellBinding
import com.architectcoders.aacboard.ui.fragments.stateholder.EditBoardCellState
import com.architectcoders.aacboard.ui.fragments.stateholder.buildEditBoardCellState
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.loadUrl
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditBoardCellFragment : Fragment(R.layout.fragment_edit_board_cell) {
    private var _binding: FragmentEditBoardCellBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditBoardCellViewModel by viewModel()

    private lateinit var editBoardCellState: EditBoardCellState


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditBoardCellBinding.inflate(inflater, container, false)
        editBoardCellState = buildEditBoardCellState()
        initViews()
        collectState()
        checkSearchResponse()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            pictogram.setOnClickListener {
                editBoardCellState.onSearchPictogram()
            }

            saveButton.setOnClickListener {
                viewModel.onSaveClicked(keyword.text.toString())
            }

            cancelButton.setOnClickListener {
                editBoardCellState.onCancel()
            }
        }
    }

    private fun collectState() {
        viewModel.state.let { uiStateFlow ->
            diff(uiStateFlow, { it.column }, ::onColumnChanged)
            diff(uiStateFlow, { it.row }, ::onRowChanged)
            diff(uiStateFlow, { it.pictogram }, ::onPictogramChanged)
            diff(uiStateFlow, { it.exit }, ::onExitChanged)
        }
    }

    private fun onColumnChanged(column: Int) {
        binding.columnLabel.text = getString(R.string.column_label, column)
    }

    private fun onRowChanged(row: Int) {
        binding.rowLabel.text = getString(R.string.row_label, row)
    }

    private fun onPictogramChanged(pictogram: PictogramUI?) {
        pictogram?.let {
            binding.pictogram.loadUrl(it.url)
            binding.keyword.setText(it.keyword)
        }
    }

    private fun onExitChanged(exit: Boolean) {
        if (exit) editBoardCellState.onCancel()
    }

    private fun checkSearchResponse() {
        editBoardCellState.checkSearchResponse {
            viewModel.onUpdatePictogram(it)
        }
    }

}