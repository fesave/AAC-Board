package com.architectcoders.aacboard.ui.fragments.stateholder

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.datasource.getMessage
import com.architectcoders.aacboard.ui.model.PictogramUI
import com.architectcoders.aacboard.ui.utils.returnNavigationResult

class SearchPictogramsState(
    private val context: Context,
    private val navController: NavController,
) {

    fun onPictogramSelected(selectedPictogram: PictogramUI?) {
        selectedPictogram?.let {
            navController.returnNavigationResult(selectedPictogram)
        }
    }

    fun onCancel() {
        navController.popBackStack()
    }

    fun onError(
        error: com.architectcoders.aacboard.domain.data.Error?,
        onErrorProcessed: () -> Unit,
    ) {
        error?.let {
            Toast.makeText(context, it.getMessage(context), Toast.LENGTH_SHORT)
                .show()
            onErrorProcessed()
        }
    }
}

fun Fragment.buildSearchPictogramsState(
    context: Context = requireContext(),
    navController: NavController = findNavController()
) = SearchPictogramsState(context, navController)