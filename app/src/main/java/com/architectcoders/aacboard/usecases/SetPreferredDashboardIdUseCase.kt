package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.model.PictogramsRepository

class SetPreferredDashboardIdUseCase(
    private val repository: PictogramsRepository
) : suspend (Int) -> Unit {

    override suspend fun invoke(id: Int) {
        repository.setPreferredDashboardId(id)
    }
}