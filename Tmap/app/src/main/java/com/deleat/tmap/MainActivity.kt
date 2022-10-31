package com.deleat.tmap

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import com.skt.Tmap.TMapView
import kotlin.concurrent.thread
import kotlin.reflect.typeOf


class MainActivity : AppCompatActivity() {

    lateinit var tMapView : TMapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        val btn = findViewById<Button>(R.id.linebutton)
        btn.setOnClickListener {
            findPath()
        }
    }
    private fun initialize() {
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("l7xx354154aaacb140ec8a94dfd97067bb2a")
        val linearLayoutTmap:LinearLayout = findViewById<View>(R.id.linearLayoutTmap) as LinearLayout
        linearLayoutTmap.addView(tMapView)
    }

    private fun findPath() {
        thread {
            val tMapPointStart = TMapPoint(37.570841, 126.985302)
            val tMapPointEnd = TMapPoint(37.551135, 126.988205)
            try {
                var tMapPolyLine : TMapPolyLine = TMapData().findPathDataWithType(TMapData.TMapPathType.CAR_PATH ,tMapPointStart, tMapPointEnd)
                println("컬러 프린트 전")
                println(tMapPolyLine.lineColor)
//            tMapPolyLine.lineColor = Color.BLUE
                println("테스트3")
                tMapPolyLine.setLineWidth(2F)
                println("테스트4")
                tMapView.addTMapPolyLine("Line1", tMapPolyLine)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}
