package com.example.geekhub.retrofit

import com.example.geekhub.data.DeliveryList

import com.example.geekhub.data.LocationInfo
import com.example.geekhub.data.NextSpotInfo
import com.example.geekhub.data.LoginRequest
import com.example.geekhub.data.LoginResponse
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
        @Part("spotId") spotId : RequestBody,
        @Part("deliveryTime") deliveryTime : RequestBody
    ):Call<String>

    @GET("spot/work/{driverIdx}")
    fun getlist(
        @Path("driverIdx") driverIdx : Int
    ):Call<DeliveryList>

    @PUT("spot/update")
    fun changestate(
        @Body spotId : SpotBody
    ):Call<String>
    @POST("location/sendLog")
    fun sendLocationLog(
        @Body locationInfo : LocationInfo
    ) :Call<String?>
    @GET("spot/nextInfo/{driverIdx}")
    fun nextWork(
        @Path("driverIdx") driverIdx : Int
    ):Call<NextSpotInfo>
    @POST("auth/login")
    fun login(
        @Body loginRequest : LoginRequest
    ):Call<LoginResponse>

}