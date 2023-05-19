package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.model.PictogramsRepository

class DeleteDashboardUseCase(private val repository: PictogramsRepository): suspend (Int) -> Unit{

    override suspend fun invoke(id: Int) {
        repository.deleteDashboard(id)
    }


}
