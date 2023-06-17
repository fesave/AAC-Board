package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.databinding.FragmentSearchPictogramsBinding
import com.architectcoders.aacboard.datasource.getMessage
import com.architectcoders.aacboard.domain.data.Error
import com.architectcoders.aacboard.ui.fragments.adapter.PictogramsSearchAdapter
import com.architectcoders.aacboard.ui.fragments.viewmodel.SearchPictogramsViewModel
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.returnNavigationResult
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
            pictogramList.layoutManager = GridLayoutManager(requireContext(), NBR_ITEMS_PER_ROW)
            pictogramList.adapter = adapter
            searchButton.setOnClickListener {
                viewModel.onSearchClicked(queryText.text.toString())
            }
            cancelButton.setOnClickListener {
                findNavController().returnNavigationResult(null)
            }
        }
    }

    private fun collectState() {
        viewModel.state.let { uiStateFlow ->
            diff(uiStateFlow, { it.loading }, ::onLoadingChanged)
            diff(uiStateFlow, { it.foundPictograms }, ::onFoundPictogramsChanged)
            diff(uiStateFlow, { it.error }, ::onShowError)
            diff(uiStateFlow, { it.searchString }, ::onQueryStringChanged)
            diff(uiStateFlow, { it.selectedPictogram }, ::onSelectedPictogramChanged)
        }
    }

    private fun onLoadingChanged(loading: Boolean) {
        if (loading) {
            showLoading()
        } else {
            showFoundPictograms()
        }
    }

    private fun onFoundPictogramsChanged(foundPictograms: List<PictogramUI>) {
        adapter.updateItems(foundPictograms)
    }

    private fun onQueryStringChanged(query: String) {
        binding.queryText.setText(query)
    }

    private fun onSelectedPictogramChanged(selectedPictogram: PictogramUI?) {
        selectedPictogram?.let {
            Toast.makeText(requireContext(), "Selected Cell: ${it.url}", Toast.LENGTH_SHORT).show()
            findNavController().returnNavigationResult(selectedPictogram)
        }
    }


    private fun showLoading() {
        with(binding) { showView(progressBarContainer) }
    }

    private fun showFoundPictograms() {
        with(binding) { showView(foundPictogramsContainer) }
    }


    private fun onShowError(error: Error?) {
        error?.let {
            Toast.makeText(requireContext(), it.getMessage(requireContext()), Toast.LENGTH_SHORT)
                .show()
            viewModel.resetError()
        }
    }

}