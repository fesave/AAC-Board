package com.architectcoders.aacboard.domain.use_case.dashboard.get

import com.architectcoders.aacboard.domain.repository.PictogramsRepository
import kotlinx.coroutines.flow.Flow

class GetPreferredDashboardIdUseCase(
    private val repository: PictogramsRepository
) : () -> Flow<Int> {

    override fun invoke(): Flow<Int> = repository.getPreferredDashboardId()

}