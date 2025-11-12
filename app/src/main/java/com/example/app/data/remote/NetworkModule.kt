package com.example.app.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    //private const val BASE_URL = "http://35.173.75.94:8082/"
    //private const val BASE_URL = "http://98.90.175.159:8080/"//Carlos
    private const val BASE_URL = "http://35.173.75.94:8080/"//Eduardo
    //private const val BASE_URL = "http://98.95.22.3:8080/"//Karina


    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
