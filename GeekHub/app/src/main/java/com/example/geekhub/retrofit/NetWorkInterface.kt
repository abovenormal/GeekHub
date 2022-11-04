package com.example.geekhub.retrofit

import com.example.geekhub.data.DeliveryList
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface NetWorkInterface {
    @Multipart
    @POST("s3/upload")
    fun sendimage(
        @Part image : MultipartBody.Part
    ):Call<String>
    @GET("spot/work/1")
    fun getlist():Call<DeliveryList>
}