package com.architectcoders.aacboard.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.ItemSearchPictogramBinding
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.loadUrl

class PictogramsSearchAdapter(
    private val onPictogramClicked: (PictogramUI?) -> Unit,
) : RecyclerView.Adapter<PictogramsSearchAdapter.ViewHolder>() {

    private var cells: List<PictogramUI> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.url == new.url },
    )

    fun updateItems(newItems: List<PictogramUI>) {
        cells = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSearchPictogramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cells[position])
    }

    inner class ViewHolder(private val binding: ItemSearchPictogramBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PictogramUI) {
            with(binding) {
                val placeHolder =
                    ResourcesCompat.getDrawable(root.resources, R.drawable.ic_image_placeholder, null)
                item.url.let { url -> cellPictogram.loadUrl(url, placeHolder) }
                root.setOnClickListener { onPictogramClicked(item) }
            }
            binding.cellPictogram.requestLayout()
        }
    }
}