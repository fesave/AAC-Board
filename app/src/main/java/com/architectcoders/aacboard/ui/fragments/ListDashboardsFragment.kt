package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentListDashboardsBinding
import com.architectcoders.aacboard.ui.fragments.adapter.ListDashboardAdapter
import com.architectcoders.aacboard.ui.fragments.stateholder.ListDashboardsState
import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel
import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel.DashboardUiItem
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.showView
import com.architectcoders.aacboard.ui.utils.toggleVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListDashboardsFragment : Fragment() {

    private var _binding: FragmentListDashboardsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListDashboardsViewModel by viewModel()

    private lateinit var listDashboardsState: ListDashboardsState

    private val adapter: ListDashboardAdapter =
        ListDashboardAdapter(
            onPreferredSelected = { selectedId -> viewModel.onPreferredDashboardClicked(selectedId) },
            onDashboardNavigateClicked = { id -> listDashboardsState.onDashboardNavigateClicked(id) },
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListDashboardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDashboardsState = ListDashboardsState(findNavController())

        initViews()
        collectState()
    }

    private fun initViews() {
        binding.apply {
            dashboardList.layoutManager = LinearLayoutManager(requireContext())
            dashboardList.adapter = adapter
            newDashboardButton.setOnClickListener {
                listDashboardsState.onNewDashboardClicked()
            }
            dashboardCreateIcon.setOnClickListener {
                listDashboardsState.onNewDashboardClicked()
            }
        }
    }

    private fun collectState() {
        viewModel.state.let { uiState ->
            diff(uiState, { it.loading }, ::onLoadingChanged)
            diff(uiState, { it.dashboards }, ::onDashBoardListChanged)
        }
    }

    private fun onLoadingChanged(loading: Boolean) {
        with(binding) {
            progressBarContainer.toggleVisibility(loading)
            viewAnimator.toggleVisibility(!loading)
        }
    }

    private fun onDashBoardListChanged(dashboards: List<DashboardUiItem>) {
        if (dashboards.isNotEmpty()) {
            showDashboardsList(dashboards)
        } else {
            showEmptyDashboardList()
        }
    }

    private fun showDashboardsList(dashboards: List<DashboardUiItem>) {
        adapter.update(dashboards)
        with(binding) {
            showView(binding.dashboardListContainer)
        }
    }

    private fun showEmptyDashboardList() {
        with(binding) {
            showView(binding.dashboardEmptyListContainer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}