package com.mobcoin.app.api.managers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiManager<T>(baseUrl: String, serviceClass: Class<T>) {
    private var service: T = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(serviceClass)

    fun getService(): T {
        return this.service
    }
}