package com.architectcoders.aacboard.model

import retrofit2.http.GET
import retrofit2.http.Path

interface ArasaacService {

    @GET("pictograms/{locale}/search/{searchString}")
    suspend fun searchPictos(
        @Path("locale")locale: String,
        @Path("searchString") searchString: String
    ): List<ArasaacPictogram>
}