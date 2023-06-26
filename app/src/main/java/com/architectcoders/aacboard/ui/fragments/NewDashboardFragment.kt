package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.databinding.FragmentNewDashboardBinding
import com.architectcoders.aacboard.ui.fragments.stateholder.NewDashBoardState
import com.architectcoders.aacboard.ui.fragments.stateholder.buildNewDashBoardCellState
import com.architectcoders.aacboard.ui.fragments.viewmodel.NewDashBoardViewModel
import com.architectcoders.aacboard.ui.utils.loadUrl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            viewModel.state.collectLatest { uiState ->
                binding.progressCircular.isVisible = uiState.isLoading
                binding.buttonNewDashboardSave.isEnabled = !uiState.isLoading
                uiState.pictogram?.let {
                    binding.ivNewDashboardImage.loadUrl(it.url)
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.buttonNewDashboardCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivNewDashboardImage.setOnClickListener {
            newDashBoardState.onSearchPictogram()
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