package com.deleat.geekhub

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.PathOverlay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback{
    companion object{
        lateinit var naverMap: NaverMap
    }

    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val APIKEY_ID = "dsd1syl3bg"
        val APIKEY = "WuDvwWaBFLTvSzduM94HkD7wLsrsInigPd4SFHF0"
        //레트로핏 객체 생성
        val retrofit =
            Retrofit.Builder().baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(NaverAPI::class.java)
        //근처에서 길찾기
        val callgetPath =
            api.getPath(APIKEY_ID, APIKEY, "129.089441, 35.231100", "129.084454, 35.228982")
        callgetPath.enqueue(object : Callback<ResultPath> {
            override fun onResponse(
                call: Call<ResultPath>,
                response: Response<ResultPath>
            ) {
                var path_cords_list = response.body()?.route?.traoptimal
                val path = PathOverlay()
                val path_container: MutableList<LatLng>? = mutableListOf(LatLng(0.1, 0.1))
                for (path_cords in path_cords_list!!) {
                    for (path_cords_xy in path_cords?.path!!) {
                        path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                    }
                }
                path.coords = path_container?.drop(1)!!
                path.color = Color.RED
                path.map = naverMap

                if (path.coords != null) {
                    val cameraUpdate = CameraUpdate.scrollTo(path.coords[0]!!)
                        .animate(CameraAnimation.Fly, 3000)
                    naverMap!!.moveCamera(cameraUpdate)

                    Toast.makeText(this@MainActivity, "경로안내내", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
    }
}