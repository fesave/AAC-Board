package com.architectcoders.aacboard.network

import com.architectcoders.aacboard.network.service.ArasaacService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ArasaacNetworkInstance {

    fun getOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    fun createArasaacNetworkInstance(
        baseUrl: String = ARASAAC_BASE_URL,
        okHttpClient: OkHttpClient
    ): ArasaacService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ArasaacService::class.java)
    }
}