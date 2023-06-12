package com.architectcoders.aacboard.ui.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.returnNavigationResult(result: T? = null, key: String = "result") {
    val navController = findNavController()
    navController.previousBackStackEntry?.savedStateHandle?.set(key, result)
    navController.popBackStack()
}
