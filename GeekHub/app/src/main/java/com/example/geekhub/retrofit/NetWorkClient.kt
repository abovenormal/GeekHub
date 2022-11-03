package com.example.geekhub.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {
    private const val BASE_URL = "http://k7c205.p.ssafy.io:9012/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val GetNetwork:NetWorkInterface = retrofit.create(NetWorkInterface::class.java)

}