package com.architectcoders.aacboard.domain.use_case.location

import com.architectcoders.aacboard.domain.repository.RegionRepository


class GetLastUserRegionUseCase(
    private val regionRepository: RegionRepository
): suspend () -> String {

    override suspend fun invoke(): String {
        return regionRepository.getLastUserRegion()
    }
}
