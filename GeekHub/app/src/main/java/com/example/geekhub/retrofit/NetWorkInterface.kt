package com.example.geekhub.retrofit

import com.example.geekhub.data.SendImageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetWorkInterface {
    @Multipart
    @POST("s3/upload")
    fun sendimage(
        @Part image : MultipartBody.Part
    ):Call<String>
}