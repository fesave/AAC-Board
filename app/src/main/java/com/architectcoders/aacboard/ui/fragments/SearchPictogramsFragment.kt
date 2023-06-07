package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentSearchPictogramsBinding
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.ui.fragments.adapter.PictogramsSearchAdapter
import com.architectcoders.aacboard.ui.fragments.viewmodel.SearchPictogramsViewModel
import com.architectcoders.aacboard.ui.utils.launchAndCollect
import com.architectcoders.aacboard.ui.utils.showView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchPictogramsFragment : Fragment(R.layout.fragment_search_pictograms) {

    companion object {
        const val NBR_ITEMS_PER_ROW = 4
    }
    private var _binding: FragmentSearchPictogramsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchPictogramsViewModel by viewModel()

    private val adapter: PictogramsSearchAdapter =
        PictogramsSearchAdapter { viewModel.onPictogramClicked(it) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchPictogramsBinding.inflate(inflater, container, false)
        initViews()
        collectState()
        return binding.root
    }


    private fun initViews() {
        binding.apply {
            pictogramList.layoutManager = GridLayoutManager(requireContext(),NBR_ITEMS_PER_ROW)
            pictogramList.adapter = adapter
            searchButton.setOnClickListener {
                viewModel.onSearchClicked(binding.queryText.text.toString())
            }
            cancelButton.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.launchAndCollect(viewModel.state) { uiState ->
            updateUiState(uiState)
        }
    }

    private fun updateUiState(newUiState: SearchPictogramsViewModel.SearchPictogramUiState) {
        with(newUiState) {
            if (loading) {
                showLoading()
            } else {
                showFoundPictograms()
                searchString.let { updateQueryString(it)}
                error?.let { showError(it) }
                selectedPictogram?.let {
                    showSelection(it)
                }
                foundPictograms.let { updatePictograms(it) }

            }
        }
    }

    private fun updateQueryString (query:String) {
        binding.queryText.setText (query)
    }

    private fun updatePictograms (list: List<CellPictogram>) {
        adapter.updateItems(list)
    }

    private fun showLoading() {
        with(binding) { showView(progressBarContainer) }
    }

    private fun showFoundPictograms() {
        with(binding) { showView(foundPictogramsContainer) }
    }

    private fun showSelection(selectedCell: CellPictogram) {
        Toast.makeText(requireContext(),"Selected Cell: ${selectedCell.url}", Toast.LENGTH_SHORT).show()
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(),"showError: $error", Toast.LENGTH_SHORT).show()
    }

}