package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentMainDashboardBinding
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import com.architectcoders.aacboard.ui.fragments.adapter.DashboardCellsAdapter
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

    private val viewModel: MainDashboardViewModel by viewModel()

    private val dashboardCellsAdapter = DashboardCellsAdapter { viewModel.onPictogramClicked(it) }

    private lateinit var textToSpeech: TextToSpeech

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
        setupMenu()
        initViews()
        initTextToSpeech()
        collectState()
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main_dashboard, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    navigateToDashboardList()
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
                navigateToDashboardList()
            }
            pictogramSelection.setOnClearLastSelectionClickListener {
                viewModel.onClearLastSelectionClicked()
            }
            pictogramSelection.setOnClearSelectionClickListener {
                viewModel.onClearSelectionClicked()
            }
            pictogramSelection.setOnSpeakClickListener { selection ->
                launchSpeechForSelection(selection)
            }
        }
    }

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(context) { textToSpeech.language = Locale.getDefault() }
    }
    private fun launchSpeechForSelection(selection: List<String>) {
        textToSpeech.speak(selection.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun collectState() {
        viewModel.state.let { uiStateFlow ->
            diff(uiStateFlow, { it.loading }, ::onLoadingChanged)
            diff(uiStateFlow, { it.dashboard }, ::onSelectedDashboardChanged)
            diff(uiStateFlow, { it.error }, { error -> error?.let(::showError) })
            diff(uiStateFlow, { it.selectedCellPictograms }, ::updateSelectedPictograms)
        }
    }

    private fun updateSelectedPictograms(selectedCellPictograms: List<CellPictogram>) {
        binding.pictogramSelection.onNewSelection(selectedCellPictograms)
    }

    private fun onLoadingChanged(visible: Boolean) {
        with(binding) {
            progressBarContainer.toggleVisibility(visible)
            viewAnimator.toggleVisibility(!visible)
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

    private fun showError(error: String) {
        Log.d(TAG, "showError: $error")
    }

    private fun navigateToDashboardList() {
        findNavController().navigate(R.id.action_mainDashboard_to_listDashboards)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        textToSpeech.stop()
    }

    companion object {
        private val TAG: String = MainDashboardFragment::class.java.name
    }
}