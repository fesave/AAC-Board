package com.architectcoders.aacboard.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.ItemDashboardBinding
import com.architectcoders.aacboard.ui.fragments.viewmodel.ListDashboardsViewModel
import com.architectcoders.aacboard.ui.utils.basicDiffUtil
import com.architectcoders.aacboard.ui.utils.loadUrl

class ListDashboardAdapter(
    private val onPreferredSelected: (Int) -> Unit,
    private val onDashboardNavigateClicked: (Int) -> Unit,
) : RecyclerView.Adapter<ListDashboardAdapter.ViewHolder>() {

    private var dashboards: List<ListDashboardsViewModel.DashboardUiItem> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id },
    )

    fun update(newItems: List<ListDashboardsViewModel.DashboardUiItem>) {
        dashboards = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dashboards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dashboards[position])
    }

    inner class ViewHolder(private val binding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dashboard: ListDashboardsViewModel.DashboardUiItem) {
            binding.apply {
                if (dashboard.image.isNotEmpty()) dashboardIcon.loadUrl(dashboard.image)
                name.text = dashboard.name
                preferredIcon.setImageDrawable(
                    AppCompatResources.getDrawable(
                        itemView.context,
                        if (dashboard.isPreferred) R.drawable.ic_filled_star else R.drawable.ic_outline_star,
                    ),
                )
                listOf(preferredIcon, name).forEach { view ->
                    view.setOnClickListener { onPreferredSelected(dashboard.id) }
                }
                navigateToDetailIcon.setOnClickListener {
                    onDashboardNavigateClicked(dashboard.id)
                }
            }
        }
    }
}