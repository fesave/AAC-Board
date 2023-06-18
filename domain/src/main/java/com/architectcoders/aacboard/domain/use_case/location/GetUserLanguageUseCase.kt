package com.architectcoders.aacboard.domain.use_case.location

import com.architectcoders.aacboard.domain.repository.RegionRepository


class GetUserLanguageUseCase(
    private val regionRepository: RegionRepository
) : suspend () -> String {

    override suspend fun invoke(): String {
        return regionRepository.getUserLanguage()
    }
}
