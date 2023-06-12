package com.architectcoders.aacboard.domain.use_case.dashboard.delete

import com.architectcoders.aacboard.domain.repository.PictogramsRepository


class DeleteDashboardUseCase(private val repository: PictogramsRepository): suspend (Int) -> Unit{

    override suspend fun invoke(id: Int) {
        repository.deleteDashboard(id)
    }
}
