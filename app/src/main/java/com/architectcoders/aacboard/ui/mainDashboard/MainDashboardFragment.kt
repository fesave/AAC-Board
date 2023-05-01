package com.architectcoders.aacboard.ui.mainDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.FragmentMainDashboardBinding

class MainDashboardFragment : Fragment() {

    private var _binding: FragmentMainDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainDashboardBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.navigateButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_mainDashboard_to_listDashboards)
        }
    }
}