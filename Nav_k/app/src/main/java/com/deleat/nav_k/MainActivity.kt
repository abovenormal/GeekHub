package com.deleat.nav_k

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.navi.Constants
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.Location
import com.kakao.sdk.navi.model.NaviOption

class MainActivity : AppCompatActivity() {
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//        val mapView = MapView(this)
//        binding.clKakaoMapView.addView(mapView)
    }

    override fun onResume() {
        super.onResume()
        btn.setOnClickListener {
            println("클릭")
            if (NaviClient.instance.isKakaoNaviInstalled(this)){
                println("성공")
                startActivity(
                    NaviClient.instance.navigateIntent(
                        Location("카카오 판교오피스", "127.108640", "37.402111"),
                        NaviOption(coordType = CoordType.WGS84)
                    )
                )
            } else{
                println("실패")
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.WEB_NAVI_INSTALL)
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            }
        }
    }


}