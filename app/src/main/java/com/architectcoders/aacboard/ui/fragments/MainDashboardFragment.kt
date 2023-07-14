package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentMainDashboardBinding
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.ui.fragments.adapter.DashboardCellsAdapter
import com.architectcoders.aacboard.ui.fragments.stateholder.MainDashboardState
import com.architectcoders.aacboard.ui.fragments.stateholder.buildMainDashboardState
import com.architectcoders.aacboard.ui.fragments.viewmodel.MainDashboardViewModel
import com.architectcoders.aacboard.ui.utils.diff
import com.architectcoders.aacboard.ui.utils.showView
import com.architectcoders.aacboard.ui.utils.toggleVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@Suppress("TooManyFunctions")
class MainDashboardFragment : Fragment() {

    private var _binding: FragmentMainDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainState: MainDashboardState

    private val viewModel: MainDashboardViewModel by viewModel()

    private val dashboardCellsAdapter = DashboardCellsAdapter { viewModel.onPictogramClicked(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainDashboardState()

        setupMenu()
        initViews()
        collectState()
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main_dashboard, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    mainState.onDashboardListIconClicked()
                    return true
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )
    }

    private fun initViews() {
        binding.apply {
            dashboardCellsList.adapter = dashboardCellsAdapter
            dashboardListIcon.setOnClickListener {
                mainState.onDashboardListIconClicked()
            }
            pictogramSelection.setOnClearLastSelectionClickListener {
                viewModel.onClearLastSelectionClicked()
            }
            pictogramSelection.setOnClearSelectionClickListener {
                viewModel.onClearSelectionClicked()
            }
            pictogramSelection.setOnSpeakClickListener { selection ->
                mainState.onTextToSpeechRequired(selection)
            }
        }
    }

    private fun collectState() {
        viewModel.state.let { uiStateFlow ->
            diff(uiStateFlow, { it.loading }, ::onLoadingChanged)
            diff(uiStateFlow, { it.dashboard }, ::onSelectedDashboardChanged)
            diff(uiStateFlow, { it.selectedCellPictograms }, ::updateSelectedPictograms)
        }
    }

    private fun updateSelectedPictograms(selectedCellPictograms: List<CellPictogram>) {
        binding.pictogramSelection.onNewSelection(selectedCellPictograms)
    }

    private fun onLoadingChanged(loading: Boolean) {
        with(binding) {
            progressBarContainer.toggleVisibility(loading)
            viewAnimator.toggleVisibility(!loading)
        }
    }

    private fun onSelectedDashboardChanged(dashboard: DashboardWithCells?) {
        dashboard?.let { showDashboard(it) } ?: showNoDashboardSelected()
    }

    private fun showDashboard(dashboard: DashboardWithCells) {
        with(binding) {
            dashboardCellsList.layoutManager =
                GridLayoutManager(requireContext(), dashboard.columns)
            dashboardCellsAdapter.apply {
                updateDashboardDimens(dashboard.columns, dashboard.rows)
                updateItems(dashboard.cells)
            }
            showView(dashboardContainer)
        }
    }

    private fun showNoDashboardSelected() {
        with(binding) { showView(noDashboardSelectedContainer) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainState.onDestroyView()
        _binding = null
    }
}