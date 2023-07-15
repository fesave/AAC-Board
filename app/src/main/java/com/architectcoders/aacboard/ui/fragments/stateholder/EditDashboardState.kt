package com.architectcoders.aacboard.ui.fragments.stateholder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.fragments.EditDashboardFragmentDirections

class EditDashboardState(
    private val navController: NavController,
) {

    fun onCellClicked(dashBoardId: Int, column: Int, row: Int) {
        val action = EditDashboardFragmentDirections.actionEditDashboardToEditBoardCell(
            dashBoardId,
            row,
            column
        )
        navController.navigate(action)
    }
}

fun Fragment.buildEditDashboardState(
    navController: NavController = findNavController(),
) = EditDashboardState(navController)