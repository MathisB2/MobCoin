package com.mobcoin.app.domain.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object FNGNetworkDataSource {
    private const val BASE_URL = "https://api.alternative.me/"


    private val logInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag(
            "OkHttp"
        ).d(message)
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    private val okHttp = OkHttpClient.Builder()
        .addNetworkInterceptor(logInterceptor)
        .build()


    private val retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(FNGNetworkDataSource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val apiService = retrofit.create(FNGService::class.java)
}