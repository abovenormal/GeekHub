package com.example.geekhub.retrofit

import com.example.geekhub.data.*

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetWorkInterface {
    @Multipart
    @POST("api/s3/upload")
    fun sendimage(
        @Part image : MultipartBody.Part,
        @Part("userId") userId : RequestBody,
        @Part("spotId") spotId : RequestBody,
        @Part("deliveryTime") deliveryTime : RequestBody
    ):Call<String>

    @GET("api/spot/work/{driverIdx}")
    fun getlist(
        @Path("driverIdx") driverIdx : Int
    ):Call<DeliveryList>

    @PUT("api/spot/update")
    fun changestate(
        @Body spotId : SpotBody
    ):Call<String>
    @POST("api/location/sendLog")
    fun sendLocationLog(
        @Body locationInfo : LocationInfo
    ) :Call<String?>
    @GET("api/spot/nextInfo/{driverIdx}")
    fun nextWork(
        @Path("driverIdx") driverIdx : Int
    ):Call<NextSpotInfo>
    @POST("api/auth/login")
    fun login(
        @Body loginRequest : LoginRequest
    ):Call<LoginResponse>

    @GET("chat/room")
    fun findChatRoom(
        @Query("userIdx") userIdx : String?
    ):Call<ChattingRoomResponse>

    @GET("api/admin/users")
    fun findChatMember(
        @Query("localSchool") localSchool : String?
    ):Call<List<Member>>

    @GET("chat/message")
    fun receiveMessage(
        @Query("roomIdx") roomIdx : String?
    ):Call<ArrayList<messageData>>
}