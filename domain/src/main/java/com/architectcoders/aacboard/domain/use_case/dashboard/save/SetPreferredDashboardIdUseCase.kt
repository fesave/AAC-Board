package com.architectcoders.aacboard.domain.use_case.dashboard.save

import com.architectcoders.aacboard.domain.repository.PictogramsRepository

class SetPreferredDashboardIdUseCase(
    private val repository: PictogramsRepository
) : suspend (Int) -> Unit {

    override suspend fun invoke(id: Int) {
        repository.setPreferredDashboardId(id)
    }
}