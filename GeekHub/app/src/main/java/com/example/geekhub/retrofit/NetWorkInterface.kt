package com.example.geekhub.retrofit

import com.example.geekhub.data.SendImageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetWorkInterface {
    @POST("s3/upload")
    fun sendimage(
        @Body image : MultipartBody.Part
    ): Call<SendImageResponse>
}