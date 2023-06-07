package com.architectcoders.aacboard.domain.use_case.search

import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.repository.PictogramsRepository


class SearchPictogramsUseCase(
    private val repository: PictogramsRepository
): suspend (String) -> List<CellPictogram>{

    override suspend fun invoke(searchString: String): List<CellPictogram> {
        return repository.searchPictograms(searchString)
    }
}
