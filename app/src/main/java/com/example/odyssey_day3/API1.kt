package com.example.odyssey_day3

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API1 {
    val apiInterface: Api1_Interface by lazy {  //需要使用到時，只會初始化一次

        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val myOkHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        //retrofit實體
        return@lazy Retrofit.Builder()
            .baseUrl("https://0ffc3d41.ap.ngrok.io")
            .client(myOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api1_Interface::class.java)
    }
}