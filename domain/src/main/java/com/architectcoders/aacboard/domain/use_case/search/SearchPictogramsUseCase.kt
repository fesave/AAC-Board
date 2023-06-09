package com.architectcoders.aacboard.domain.use_case.search

import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.domain.repository.PictogramsRepository


class SearchPictogramsUseCase(
    private val repository: PictogramsRepository
): suspend (String) -> Response<List<CellPictogram>> {

    override suspend fun invoke(searchString: String): Response<List<CellPictogram>> {
        return repository.searchPictograms(searchString)
    }
}
