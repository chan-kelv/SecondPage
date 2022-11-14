package com.kelvinfocus.secondpage

import com.kelvinfocus.secondpage.auth.AuthRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val retrofitAuthClient: Retrofit by lazy {
        val loggingIntercept = HttpLoggingInterceptor()
        loggingIntercept.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingIntercept)
            .build()

        Retrofit.Builder()
            .baseUrl(AuthRepository.REDDIT_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}