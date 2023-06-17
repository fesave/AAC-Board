package com.architectcoders.aacboard.ui.fragments.stateholder

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.utils.getNavigationResult

class EditBoardCellState(
    private val navController: NavController
) {

    fun checkSearchResponse(onSearchResponse: (PictogramUI?) -> Unit) {
        val pictogram: PictogramUI? = navController.getNavigationResult()
        pictogram?.let { onSearchResponse(pictogram) }
    }

    fun onCancel() {
        //Destination.BACK -> findNavController().popBackStack()
        navController.navigate(R.id.action_editBoardCell_to_mainDashboard)
    }

    fun onSearchPictogram() {
        navController.navigate(R.id.action_editBoardCell_to_searchPictograms)
    }

}

fun Fragment.buildEditBoardCellState(
    navController: NavController = findNavController()
) = EditBoardCellState(navController)
