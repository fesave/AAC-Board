package com.architectcoders.aacboard.ui.utils

import androidx.navigation.NavController


fun <T> NavController.getNavigationResult(key: String = "result") =
    currentBackStackEntry?.savedStateHandle?.get<T?>(key)

fun <T> NavController.returnNavigationResult(result: T? = null, key: String = "result") {
    previousBackStackEntry?.savedStateHandle?.set(key, result)
    popBackStack()
}
