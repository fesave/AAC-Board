package com.architectcoders.aacboard.ui.mainDashboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.aacboard.databinding.ViewPictogramsSelectionBinding
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.ui.utils.setViewEnabled

class PictogramsSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding: ViewPictogramsSelectionBinding
    private val adapter: PictogramsSelectionAdapter = PictogramsSelectionAdapter()
    private var selection: List<Pictogram> = emptyList()

    init {
        binding = ViewPictogramsSelectionBinding.inflate(LayoutInflater.from(context), this, true)
        binding.pictogramSelectionList.adapter = adapter
        binding.pictogramSelectionList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun onNewSelection(newSelection: List<Pictogram>) {
        selection = newSelection
        adapter.updatePictograms(selection)
        updateViewsForNewSelection(selection)
    }

    private fun updateViewsForNewSelection(newSelection: List<Pictogram>) {
        updateViewInteractionsState(allowedInteractions = newSelection.isNotEmpty())
        scrollTo(newSelection.lastIndex)
    }

    private fun scrollTo(position: Int) {
        binding.pictogramSelectionList.scrollToPosition(position)
    }

    private fun updateViewInteractionsState(allowedInteractions: Boolean) {
        binding.apply {
            speakSelection.setViewEnabled(allowedInteractions)
            clearAllSelection.setViewEnabled(allowedInteractions)
            clearLastSelection.setViewEnabled(allowedInteractions)
        }
    }

    fun setOnSpeakClickListener(action: (List<String>) -> Unit) {
        binding.speakSelection.setOnClickListener {
            action(selection.map { it.keyword })
        }
    }

    fun setOnClearSelectionClickListener(action: () -> Unit) {
        binding.clearAllSelection.setOnClickListener { action() }
    }

    fun setOnClearLastSelectionClickListener(action: () -> Unit) {
        binding.clearLastSelection.setOnClickListener { action() }
    }

}