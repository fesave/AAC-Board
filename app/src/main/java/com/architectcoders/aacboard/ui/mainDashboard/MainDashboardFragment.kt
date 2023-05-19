package com.architectcoders.aacboard.ui.mainDashboard

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.architectcoders.aacboard.App
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentMainDashboardBinding
import com.architectcoders.aacboard.domain.DashboardWithCells
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.model.PictogramsRepository
import com.architectcoders.aacboard.ui.utils.launchAndCollect
import com.architectcoders.aacboard.ui.utils.showView
import com.architectcoders.aacboard.usecases.GetDashboardUseCase
import com.architectcoders.aacboard.usecases.GetPreferredDashboardIdUseCase
import java.util.*

@Suppress("TooManyFunctions")
class MainDashboardFragment : Fragment() {

    companion object {
        private val TAG: String = MainDashboardFragment::class.java.name
    }

    private var _binding: FragmentMainDashboardBinding? = null
    private val binding get() = _binding!!

    private val repository by lazy {
        PictogramsRepository(requireActivity().application as App)
    }

    private val viewModel: MainDashboardViewModel by viewModels {
        MainDashboardViewModelFactory(
            GetPreferredDashboardIdUseCase(repository),
            GetDashboardUseCase(repository)
        )
    }

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

    private fun initTextToSpeech() {
        textToSpeech = TextToSpeech(context) { textToSpeech.language = Locale.getDefault() }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main_dashboard, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                navigateToDashboardList()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

    private fun launchSpeechForSelection(selection: List<String>) {
        textToSpeech.speak(selection.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun collectState() {
        viewLifecycleOwner.launchAndCollect(viewModel.state) { uiState ->
            updateUiState(uiState)
        }
    }

    private fun updateUiState(newUiState: MainDashboardUiState) {
        with(newUiState) {
            if (loading) {
                showLoading()
            } else {
                dashboard?.let { showDashboard(it) } ?: showNoDashboardSelected()
                error?.let { showError(it) }
                updateSelectedPictograms(selectedPictograms)
            }
        }
    }

    private fun updateSelectedPictograms(selectedPictograms: List<Pictogram>) {
        binding.pictogramSelection.onNewSelection(selectedPictograms)
    }

    private fun showLoading() {
        with(binding) { showView(progressBarContainer) }
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
}