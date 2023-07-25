package com.architectcoders.aacboard.ui.fragments.stateholder

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.fragments.EditDashboardFragmentDirections

class EditDashboardState(
    private val navController: NavController,
) {

    fun onCellClicked(dashBoardId: Int, row: Int, column: Int) {
        val action = EditDashboardFragmentDirections.actionEditDashboardToEditBoardCell(
            dashBoardId,
            row,
            column
        )
        navController.navigate(action)
    }

    fun exit() {
        navController.navigate(R.id.action_edit_dashboard_dest_to_list_dashboards_dest)
    }
}

fun Fragment.buildEditDashboardState() = EditDashboardState(navController = findNavController())