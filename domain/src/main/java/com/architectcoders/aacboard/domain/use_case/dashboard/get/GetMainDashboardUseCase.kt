package com.architectcoders.aacboard.domain.use_case.dashboard.get

import com.architectcoders.aacboard.domain.data.dashboard.DashboardWithCells
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class GetMainDashboardUseCase(
    private val getPreferredDashboardIdUseCase: GetPreferredDashboardIdUseCase,
    private val getDashboardUseCase: GetDashboardUseCase,
): suspend () -> Flow<DashboardWithCells?> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun invoke(): Flow<DashboardWithCells?> =
        getPreferredDashboardIdUseCase()
            .distinctUntilChanged()
            .flatMapLatest {
                getDashboardUseCase(it)

            }
}
