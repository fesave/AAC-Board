package com.architectcoders.aacboard.model

import retrofit2.http.GET
import retrofit2.http.Path

interface ArasaacService {

    @GET("pictograms/es/search/{searchString}")
    suspend fun searchPictos(
        @Path("searchString") searchString: String
    ): List<ArasaacPictogram>
}