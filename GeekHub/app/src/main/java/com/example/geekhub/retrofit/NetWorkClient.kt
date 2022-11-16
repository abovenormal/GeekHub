package com.example.geekhub.retrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkClient {
    private const val BASE_URL = "http://k7c205.p.ssafy.io/"

    var gson : Gson =  GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val GetNetwork: NetWorkInterface = retrofit.create(NetWorkInterface::class.java)

}