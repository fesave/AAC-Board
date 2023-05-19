package com.architectcoders.aacboard.ui.mainDashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.databinding.ItemDashboardCellBinding
import com.architectcoders.aacboard.domain.Cell
import com.architectcoders.aacboard.domain.Pictogram
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.getScreenSize
import com.architectcoders.aacboard.ui.utils.loadUrl

class DashboardCellsAdapter(
    private val onPictogramClicked: (Pictogram?) -> Unit,
) : RecyclerView.Adapter<DashboardCellsAdapter.ViewHolder>() {

    private var cells: List<Cell> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.pictogram == new.pictogram }
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

            val finalWidth = (windowWidth / columns) - marginStart - marginEnd - 20
            val finalHeight = (windowHeight / rows) - 20

            with(binding) {
                item.pictogram?.url?.let { url -> cellPictogram.loadUrl(url) }
                item.pictogram?.keyword?.let { cellPictogramKeyword.text = it }
                root.setOnClickListener { onPictogramClicked(item.pictogram) }
            }
            binding.cellPictogram.layoutParams.width = minOf(finalWidth, finalHeight)
            binding.cellPictogram.layoutParams.height = minOf(finalWidth, finalHeight)
            binding.cellPictogram.requestLayout()
        }
    }
}