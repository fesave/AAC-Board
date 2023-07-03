package com.architectcoders.aacboard.ui.fragments.stateholder

import androidx.navigation.NavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.fragments.ListDashboardsFragmentDirections

class ListDashboardsState(
    private val navController: NavController
) {

    fun onDashboardNavigateClicked(dashboardId: Int) {
        val action = ListDashboardsFragmentDirections.actionListDashboardsToEditDashboard()
        navController.navigate(action)
    }

    fun onNewDashboardClicked() {
        navController.navigate(R.id.action_listDashboards_to_newDashboard)
    }

}