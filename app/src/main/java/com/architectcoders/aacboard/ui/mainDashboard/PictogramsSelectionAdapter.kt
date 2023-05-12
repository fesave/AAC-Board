package com.architectcoders.aacboard.ui.mainDashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.databinding.ItemDashboardCellBinding
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.loadUrl

class PictogramsSelectionAdapter : RecyclerView.Adapter<PictogramsSelectionAdapter.ViewHolder>() {

    private var pictograms: List<Pictogram> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old == new }
    )

    fun updatePictograms(newSelection: List<Pictogram>) {
        pictograms = newSelection
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDashboardCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = pictograms.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pictograms[position])
    }

    inner class ViewHolder(private val binding: ItemDashboardCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pictogram: Pictogram) {
            binding.cellPictogram.loadUrl(pictogram.url)
        }
    }


}