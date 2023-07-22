package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentEditDashboardBinding
import com.architectcoders.aacboard.ui.fragments.adapter.DashboardCellsAdapter
import com.architectcoders.aacboard.ui.fragments.stateholder.EditDashboardState
import com.architectcoders.aacboard.ui.fragments.stateholder.buildEditDashboardState
import com.architectcoders.aacboard.ui.fragments.viewmodel.EditDashBoardViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditDashboardFragment : Fragment() {

    private var _binding: FragmentEditDashboardBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EditDashboardFragmentArgs>()

    private val state: EditDashboardState by lazy {
        buildEditDashboardState()
    }

    private val viewModel: EditDashBoardViewModel by viewModel {
        parametersOf(args.dashBoardId)
    }
    private val dashboardCellsAdapter = DashboardCellsAdapter(editionEnabled = true) { cell ->
        state.onCellClicked(dashBoardId = args.dashBoardId, row = cell.row, column = cell.column)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                printData(state)
            }
        }
    }

    private fun initView() {
        with(binding) {
            buttonNewDashboardDetailDelete.setOnClickListener {
                viewModel.onDeleteButtonClicked()
                findNavController().navigate(R.id.action_edit_dashboard_dest_to_list_dashboards_dest)
            }

            buttonNewDashboardDetailAccept.setOnClickListener {
                findNavController().navigate(R.id.action_edit_dashboard_dest_to_list_dashboards_dest)
            }
        }
    }

    private fun printData(state: EditDashBoardViewModel.EditDashBoardUiState) {
        with(binding) {
            progressCircularEditDashboard.isVisible = state.isLoading
            state.dashboardsWithCells?.let {
                setToolbarTitle(it.name)
                dashboardCellsList.apply {
                    adapter = dashboardCellsAdapter
                    layoutManager =
                        GridLayoutManager(requireContext(), it.columns)
                }
                dashboardCellsAdapter.apply {
                    updateDashboardDimens(it.columns, it.rows)
                    updateItems(it.cells)
                }
            }
        }
    }

    private fun setToolbarTitle(dashBoardName: String) {
        (activity as AppCompatActivity?)?.supportActionBar?.title =
            context?.getString(R.string.new_dashboard_detail_toolbar_title, dashBoardName)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}