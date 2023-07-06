package com.architectcoders.aacboard.ui.fragments.stateholder

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.fragments.NewDashboardFragmentDirections
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.utils.getNavigationResult

class NewDashBoardState(
    private val navController: NavController,
    private val context: Context?,
) {

    fun checkSearchResponse(onSearchResponse: (PictogramUI?) -> Unit) {
        val pictogram: PictogramUI? = navController.getNavigationResult()
        pictogram?.let { onSearchResponse(pictogram) }
    }

    fun onCancel() {
        navController.popBackStack()
    }

    fun onSearchPictogram() {
        navController.navigate(R.id.action_new_dashboard_dest_to_searchPictogramsFragment)
    }

    fun showError() {
        Toast.makeText(
            context,
            context?.getString(R.string.new_dashboard_error),
            Toast.LENGTH_SHORT,
        ).show()
    }

    fun onDashboardSaved(dashBoardId: Int) {
        navController.navigate(
            NewDashboardFragmentDirections.actionNewDashboardToEditDashboard(
                dashBoardId = dashBoardId,
            ),
        )
    }
}

fun Fragment.buildNewDashBoardCellState(
    navController: NavController = findNavController(),
) = NewDashBoardState(navController, context)