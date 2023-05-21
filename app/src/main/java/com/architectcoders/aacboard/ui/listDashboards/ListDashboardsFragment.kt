package com.architectcoders.aacboard.ui.listDashboards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.aacboard.App
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentListDashboardsBinding
import com.architectcoders.aacboard.model.PictogramsRepository
import com.architectcoders.aacboard.ui.utils.launchAndCollect
import com.architectcoders.aacboard.usecases.*
import kotlinx.coroutines.launch

class ListDashboardsFragment : Fragment(R.layout.fragment_list_dashboards) {

    private var _binding: FragmentListDashboardsBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListDashboardAdapter =
        ListDashboardAdapter(
            onPreferredSelected = { selectedId -> viewModel.onPreferredDashboardClicked(selectedId) },
            onNavigateToDashboard = { id -> viewModel.onDeleteDashboard(id) }
        )

    private val repository by lazy {
        PictogramsRepository(requireActivity().application as App)
    }

    private val viewModel: ListDashboardsViewModel by viewModels {
        ListDashboardsViewModelFactory(
            GetAllDashboardsUseCase(repository),
            SetPreferredDashboardIdUseCase(repository),
            GetPreferredDashboardIdUseCase(repository),
            DeleteDashboardUseCase(repository),
            SaveDashboardUseCase(repository)
        )
    }

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
                viewModel.onCreateNewDashboardClicked()
            }
        }
    }

}