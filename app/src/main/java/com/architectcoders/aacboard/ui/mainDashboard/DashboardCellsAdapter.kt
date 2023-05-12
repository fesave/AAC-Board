package com.architectcoders.aacboard.ui.mainDashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.databinding.ItemDashboardCellBinding
import com.architectcoders.aacboard.domain.DashboardCell
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.getScreenWidth
import com.architectcoders.aacboard.ui.utils.loadUrl

class DashboardCellsAdapter(
    private val onPictogramClicked: (Pictogram?) -> Unit,
) : RecyclerView.Adapter<DashboardCellsAdapter.ViewHolder>() {

    private var cells: List<DashboardCell> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.pictogram == new.pictogram }
    )

    private var spanCount: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDashboardCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cells[position])
    }

    fun updateItems(newItems: List<DashboardCell>) {
        cells = newItems
    }

    fun updateSpanCount(count: Int) {
        spanCount = count
    }

    inner class ViewHolder(private val binding: ItemDashboardCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardCell) {

            val windowWidth = binding.root.getScreenWidth()
            val layoutParams = binding.root.layoutParams as GridLayoutManager.LayoutParams
            val marginStart = layoutParams.marginStart
            val marginEnd = layoutParams.marginEnd

            val finalWidth = (windowWidth / spanCount) - marginStart - marginEnd

            with(binding.cellPictogram) {
                item.pictogram?.url?.let { url -> loadUrl(url) }
                setOnClickListener { onPictogramClicked(item.pictogram) }
            }

            binding.cellPictogram.layoutParams.width = finalWidth
            binding.cellPictogram.layoutParams.height = finalWidth
            binding.cellPictogram.requestLayout()
        }
    }
}