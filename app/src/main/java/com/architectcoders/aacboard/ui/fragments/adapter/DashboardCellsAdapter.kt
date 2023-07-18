package com.architectcoders.aacboard.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.ItemDashboardCellBinding
import com.architectcoders.aacboard.domain.data.cell.Cell
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.getScreenSize
import com.architectcoders.aacboard.ui.utils.loadUrl

class DashboardCellsAdapter(
    private val editionEnabled: Boolean = false,
    private val onPictogramClicked: (Cell) -> Unit,
) : RecyclerView.Adapter<DashboardCellsAdapter.ViewHolder>() {

    companion object {
        const val SAFE_MARGIN = 20
    }

    private var cells: List<Cell> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.cellPictogram == new.cellPictogram },
    )

    private var columns: Int = 1
    private var rows: Int = 1

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

    fun updateItems(newItems: List<Cell>) {
        cells = newItems
    }

    fun updateDashboardDimens(columns: Int, rows: Int) {
        this.columns = columns
        this.rows = rows
    }

    inner class ViewHolder(private val binding: ItemDashboardCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cell) {
            val (windowWidth, windowHeight) = binding.root.getScreenSize()
            val layoutParams = binding.root.layoutParams as GridLayoutManager.LayoutParams
            val marginStart = layoutParams.marginStart
            val marginEnd = layoutParams.marginEnd

            val finalWidth = (windowWidth / columns) - marginStart - marginEnd - SAFE_MARGIN
            val finalHeight = (windowHeight / rows) - SAFE_MARGIN

            binding.renderCell(item)
            binding.root.setOnClickListener { onPictogramClicked(item) }
            binding.cellPictogram.layoutParams.width = minOf(finalWidth, finalHeight)
            binding.cellPictogram.layoutParams.height = minOf(finalWidth, finalHeight)
            binding.cellPictogram.requestLayout()
        }
    }

    private fun ItemDashboardCellBinding.renderCell(item: Cell) {
        if (editionEnabled) {
            cellAddPictogram.isVisible = item.cellPictogram?.url == null
        }
        item.cellPictogram?.url?.let { url -> cellPictogram.loadUrl(url) }
        item.cellPictogram?.keyword?.let { cellPictogramKeyword.text = it }
    }
}