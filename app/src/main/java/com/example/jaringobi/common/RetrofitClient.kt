package com.example.jaringobi.common

import com.example.jaringobi.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    private val loggingInterceptor =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

    private val client =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.OCR_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val ocrService: OcrRequest by lazy {
        retrofit.create(OcrRequest::class.java)
    }
}
