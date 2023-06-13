package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.data.PictogramUI
import com.architectcoders.aacboard.databinding.FragmentEditBoardCellBinding
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel.Destination
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditBoardCellViewModel.Destination.*
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.getNavigationResultLiveData
import com.architectcoders.aacboard.ui.utils.loadUrl
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditBoardCellFragment : Fragment(R.layout.fragment_edit_board_cell){
    private var _binding: FragmentEditBoardCellBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: EditBoardCellFragmentArgs by navArgs()

    private val viewModel: EditBoardCellViewModel by viewModel {
        parametersOf(safeArgs.dashboardId, safeArgs.row, safeArgs.column)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditBoardCellBinding.inflate(inflater, container, false)
        initViews()
        collectState()
        observeResponse()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            pictogram.setOnClickListener {
                viewModel.onPictogramClicked()
            }

            saveButton.setOnClickListener {
                viewModel.onSaveClicked(keyword.text.toString())
            }

            cancelButton.setOnClickListener {
                onDestinationChanged(BACK)
            }
        }
    }

    private fun collectState() {
        viewModel.state.let { uiStateFlow ->
            diff(uiStateFlow, { it.column }, ::onColumnChanged)
            diff(uiStateFlow, { it.row }, ::onRowChanged)
            diff(uiStateFlow, { it.pictogram }, ::onPictogramChanged)
            diff(uiStateFlow, { it.destination }, ::onDestinationChanged)
        }
    }

    private fun onColumnChanged(column: Int) {
        binding.columnLabel.text= getString(R.string.column_label, column)
    }

    private fun onRowChanged(row: Int) {
        binding.rowLabel.text= getString(R.string.row_label, row)
    }

    private fun onPictogramChanged(pictogram: PictogramUI?) {
        pictogram?.let {
            binding.pictogram.loadUrl(it.url)
            binding.keyword.setText(it.keyword)
        }
    }

    private fun onDestinationChanged(destination: Destination?) {
        when (destination) {
            //Destination.BACK -> findNavController().popBackStack()
            BACK -> findNavController().navigate(R.id.action_editBoardCell_to_mainDashboard)
            SEARCH_PICTOGRAM -> findNavController().navigate(R.id.action_editBoardCell_to_searchPictograms)
            null -> return
        }
        viewModel.resetDestination()
    }

    private fun observeResponse() {
        val result= getNavigationResultLiveData<PictogramUI>()
        result?.observe(viewLifecycleOwner) { pictogram -> viewModel.onUpdatePictogram(pictogram) }
    }

}