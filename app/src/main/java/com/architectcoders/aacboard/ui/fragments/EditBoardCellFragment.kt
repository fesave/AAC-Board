package com.architectcoders.aacboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.data.PictogramUI
import com.architectcoders.aacboard.databinding.FragmentEditBoardCellBinding
import com.architectcoders.aacboard.ui.utils.getNavigationResultLiveData
import com.architectcoders.aacboard.ui.utils.loadUrl

class EditBoardCellFragment : Fragment(R.layout.fragment_edit_board_cell){
    private var _binding: FragmentEditBoardCellBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditBoardCellBinding.inflate(inflater, container, false)
        initViews()
        val result= getNavigationResultLiveData<PictogramUI>()
        result?.observe(viewLifecycleOwner) { pictogram -> updateUI(pictogram) }
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            pictogram.setOnClickListener {
                findNavController().navigate(R.id.action_editBoardCell_to_searchPictograms)
            }

        }
    }

    private fun updateUI(pictogram: PictogramUI?) {
        pictogram?.let {
            binding.pictogram.loadUrl(it.url)
            binding.keyword.setText(it.keyword)
        }
    }

}