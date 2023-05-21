package com.architectcoders.aacboard.usecases

import com.architectcoders.aacboard.model.PictogramsRepository
import kotlinx.coroutines.flow.Flow

class GetPreferredDashboardIdUseCase(
    private val repository: PictogramsRepository
) : () -> Flow<Int> {

    override fun invoke(): Flow<Int> = repository.getPreferredDashboardId()

}