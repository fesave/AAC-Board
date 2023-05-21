package com.architectcoders.aacboard.ui.listDashboards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.ItemDashboardBinding
import com.architectcoders.aacboard.domain.Dashboard
import com.architectcoders.aacboard.ui.utils.basicDiffUtil

class ListDashboardAdapter(
    private val onPreferredSelected: (Int) -> Unit,
    private val onNavigateToDashboard: (Int) -> Unit,
) : RecyclerView.Adapter<ListDashboardAdapter.ViewHolder>() {

    private var dashboards: List<DashboardUiItem> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    fun update(newItems: List<DashboardUiItem>) {
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

        fun bind(dashboard: DashboardUiItem) {
            binding.apply {

                preferredIcon.setImageDrawable(
                    AppCompatResources.getDrawable(
                        itemView.context,
                        if (dashboard.isPreferred) R.drawable.ic_filled_star else R.drawable.ic_outline_star
                    )
                )
                name.text = dashboard.name
                preferredIcon.setOnClickListener {
                    onPreferredSelected(dashboard.id)
                }
                navigateToDetailIcon.setOnClickListener {
                    onNavigateToDashboard(dashboard.id)
                }
            }
        }
    }

}
