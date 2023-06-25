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
import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel
import com.architectcoders.aacboard.ui.utils.launchAndCollect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListDashboardsFragment : Fragment() {

    private var _binding: FragmentListDashboardsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListDashboardsViewModel by viewModel()

    private val adapter: ListDashboardAdapter =
        ListDashboardAdapter(
            onPreferredSelected = { selectedId -> viewModel.onPreferredDashboardClicked(selectedId) },
            onNavigateToDashboard = { id -> viewModel.onDeleteDashboard(id) },
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListDashboardsBinding.inflate(inflater, container, false)
        initViews()
        collectState()
        return binding.root
    }

    private fun collectState() {
        launchAndCollect(viewModel.state) { newState ->
            adapter.update(newState.dashboards)
        }
    }

    private fun initViews() {
        binding.apply {
            dashboardList.layoutManager = LinearLayoutManager(requireContext())
            dashboardList.adapter = adapter
            newDashboardButton.setOnClickListener {
                navigateToNewDashboard()
            }
        }
    }

    private fun navigateToNewDashboard() {
        findNavController().navigate(R.id.action_listDashboards_to_newDashboard)
    }
}