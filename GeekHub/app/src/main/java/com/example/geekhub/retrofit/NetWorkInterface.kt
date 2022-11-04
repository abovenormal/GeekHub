package com.example.geekhub.retrofit

import com.example.geekhub.data.DeliveryList
import com.example.geekhub.data.SpotBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetWorkInterface {
    @Multipart
    @POST("s3/upload")
    fun sendimage(
        @Part image : MultipartBody.Part,
        @Part("userId") userId : RequestBody,
        @Part("spotId") spotId : RequestBody
    ):Call<String>
    @GET("spot/work/1")
    fun getlist():Call<DeliveryList>

    @PUT("spot/update")
    fun changestate(
        @Body spotId : SpotBody
    ):Call<String>
}