package com.architectcoders.aacboard.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

fun <T, U> Fragment.diff(
    forParentFlow: Flow<T>,
    mapToChildFlow: (T) -> U,
    body: (U) -> Unit
) {
    forParentFlow.diff(viewLifecycleOwner, mapToChildFlow, body)
}

fun <T, U> Flow<T>.diff(
    lifecycleOwner: LifecycleOwner,
    mapF: (T) -> U,
    body: (U) -> Unit
) {
    lifecycleOwner.launchAndCollect(
        flow = map(mapF).distinctUntilChanged(),
        body = body
    )
}

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit,
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}
