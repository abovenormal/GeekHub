package com.deleat.tmap

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.Tmap.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var tMapView: TMapView
    lateinit var tMapTapi: TMapTapi
    val tMapPointStart = TMapPoint(35.17764128, 126.9042024)
    val tMapPointEnd = TMapPoint(35.17764936, 126.90303593521712)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        val btn = findViewById<Button>(R.id.linebutton)
        val goNav = findViewById<Button>(R.id.navButton)
        btn.setOnClickListener {
            findPath()
        }
        goNav.setOnClickListener {
            goNav()
        }
    }

    private fun initialize() {
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("l7xx354154aaacb140ec8a94dfd97067bb2a")
        val linearLayoutTmap: LinearLayout =
            findViewById<View>(R.id.linearLayoutTmap) as LinearLayout
        linearLayoutTmap.addView(tMapView)
    }

    private fun findPath() {
        thread {
            try {
                var tMapPolyLine: TMapPolyLine = TMapData().findPathDataWithType(
                    TMapData.TMapPathType.CAR_PATH,
                    tMapPointStart,
                    tMapPointEnd
                )
                println(tMapPolyLine.lineColor)
                tMapPolyLine.lineColor = Color.RED
                tMapPolyLine.setLineWidth(2F)
                tMapView.addTMapPolyLine("Line1", tMapPolyLine)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun goNav() {
        tMapTapi = TMapTapi(this)
        if (tMapTapi.isTmapApplicationInstalled){
            tMapTapi.invokeRoute("테스트 목적지", 126.90303593521712F, 35.17764936F)
        } else{
            println("설치 안됨")
            var uri = Uri.parse(tMapTapi.tMapDownUrl[0])
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
