package com.architectcoders.aacboard.ui.fragments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<PictogramUI>) {
        cells = newItems
        notifyDataSetChanged()
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
                item.url.let { url -> cellPictogram.loadUrl(url) }
                root.setOnClickListener { onPictogramClicked(item) }
            }
            binding.cellPictogram.requestLayout()
        }
    }
}