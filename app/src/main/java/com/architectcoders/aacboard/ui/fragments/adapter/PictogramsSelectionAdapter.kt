package com.architectcoders.aacboard.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.databinding.ItemDashboardCellBinding
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.loadUrl

class PictogramsSelectionAdapter : RecyclerView.Adapter<PictogramsSelectionAdapter.ViewHolder>() {

    private var cellPictograms: List<CellPictogram> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old == new },
    )

    fun updatePictograms(newSelection: List<CellPictogram>) {
        cellPictograms = newSelection
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDashboardCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cellPictograms.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cellPictograms[position])
    }

    inner class ViewHolder(private val binding: ItemDashboardCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cellPictogram: CellPictogram) {
            binding.cellPictogram.loadUrl(cellPictogram.url)
        }
    }
}