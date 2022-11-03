package com.example.geekhub.retrofit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface NetWorkInterface {
    @Multipart
    @POST("s3/upload")
    fun sendimage(
        @Part image : MultipartBody.Part
    ):Call<String>

    @GET("s3/upload")
    fun getlist():Call<DeliveryList>

}