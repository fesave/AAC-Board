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
import com.architectcoders.aacboard.ui.utils.diff
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

    private fun onFoundPictogramsChanged(foundPictograms: List<CellPictogram>) {
        adapter.updateItems(foundPictograms)
    }

    private fun onQueryStringChanged (query:String) {
        binding.queryText.setText (query)
    }

    private fun onSelectedPictogramChanged (selectedPictogram: CellPictogram?) {
        selectedPictogram?.let {
            Toast.makeText(requireContext(),"Selected Cell: ${it.url}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showLoading() {
        with(binding) { showView(progressBarContainer) }
    }

    private fun showFoundPictograms() {
        with(binding) { showView(foundPictogramsContainer) }
    }


    private fun onShowError(error: String?) {
        error?.let {
            Toast.makeText(requireContext(), "showError: $it", Toast.LENGTH_SHORT).show()
        }
    }

}