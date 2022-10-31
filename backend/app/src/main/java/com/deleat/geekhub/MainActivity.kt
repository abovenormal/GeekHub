package com.deleat.geekhub


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import com.google.gson.JsonArray
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.PathOverlay
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), OnMapReadyCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager

        val mapFragment = fm.findFragmentById(R.id.map) as MapF
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
//        val coord = LatLng(37.5670135, 126.9783740)
//        Toast.makeText(this@MainActivity, "위도: ${coord.latitude}, 경도: ${coord.longitude}", Toast.LENGTH_SHORT).show()
        val APIKEY_ID = "dsd1syl3bg"
        val APIKEY = "WuDvwWaBFLTvSzduM94HkD7wLsrsInigPd4SFHF0"

        val GeoCode = Retrofit.Builder().
                baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/").
                addConverterFactory(GsonConverterFactory.create()).build()
        val GeoCodeApi = GeoCode.create(NaverAPI::class.java)

        val callGeoCode = GeoCodeApi.getGeoCode(APIKEY_ID, APIKEY, "서구 상무대로 773")

        callGeoCode.enqueue(object : Callback<ResultGeo>{
            override fun onResponse(call: Call<ResultGeo>, response: Response<ResultGeo>) {
                var path_cords_list = response.body()
//                val jsonArray = JsonArray(path_cords_list.toString())
                val data = path_cords_list?.addresses
                println("여기")
                println(data!!.get(0).x)
                println(data!!.get(0).y)
//                println(typeOf())
//                for (i in 1..data!!.size) {
//                    println(data[i])
//                }
            }

            override fun onFailure(call: Call<ResultGeo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        val retrofit = Retrofit.Builder().
        baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/").
        addConverterFactory(GsonConverterFactory.create()).
        build()

        val api = retrofit.create(NaverAPI::class.java)

        val callgetPath = api.getPath(APIKEY_ID, APIKEY,"126.8521202, 35.1537646", "126.8472916, 35.1467104")

        callgetPath.enqueue(object : Callback<ResultPath>{
            override fun onResponse(
                call: Call<ResultPath>,
                response: Response<ResultPath>
            ) {
                var path_cords_list = response.body()?.route?.traoptimal
                val path = PathOverlay()

                val path_container : MutableList<LatLng>? = mutableListOf(LatLng(0.1, 0.1))
                for (path_cords in path_cords_list!!){
                    for(path_cords_xy in path_cords?.path!!){
                        path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                    }
                }
                println(path_container)
                path.coords = path_container?.drop(1)!!
                path.color = Color.RED
                path.map = naverMap

                if (path.coords != null) {
                    val cameraUpdate = CameraUpdate.scrollTo(path.coords[0]!!).animate(CameraAnimation.Fly, 3000)
                    naverMap!!.moveCamera(cameraUpdate)

                    Toast.makeText(this@MainActivity, "경로안내가 시작됩니다.", Toast.LENGTH_SHORT).show()


                }
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


}