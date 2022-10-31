package com.deleat.tmap

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.TMapTapi.OnAuthenticationListenerCallback
import com.skt.Tmap.TMapView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var tMapView : TMapView
    lateinit var tMapTapi : TMapTapi
//    var tMapTapi = TMapTapi(this)
    val tMapPointStart = TMapPoint(37.570841, 126.985302)
    val tMapPointEnd = TMapPoint(37.551135, 126.988205)

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
        tMapTapi = TMapTapi(this)
        tMapTapi.setSKTMapAuthentication("l7xx354154aaacb140ec8a94dfd97067bb2a")
        val linearLayoutTmap:LinearLayout = findViewById<View>(R.id.linearLayoutTmap) as LinearLayout
        linearLayoutTmap.addView(tMapView)
        tMapTapi.setOnAuthenticationListener(object : OnAuthenticationListenerCallback {
            override fun SKTMapApikeySucceed() {
                tMapTapi.invokeNavigate("T타워", 126.984098f, 37.566385f, 0, true)
            }

            override fun SKTMapApikeyFailed(s: String) {
                println("실패")
            }
        })
    }

    private fun findPath() {
        //        thread {
//            try {
//                var tMapPolyLine : TMapPolyLine = TMapData().findPathDataWithType(TMapData.TMapPathType.CAR_PATH ,tMapPointStart, tMapPointEnd)
//                println(tMapPolyLine.lineColor)
//                tMapPolyLine.lineColor = Color.BLUE
//                tMapPolyLine.setLineWidth(2F)
//                tMapView.addTMapPolyLine("Line1", tMapPolyLine)
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
        tMapTapi.invokeTmap()
        tMapTapi.onAuthenticationListener.SKTMapApikeySucceed()

//        try {
//            tMapTapi.setSKTMapAuthentication("l7xx354154aaacb140ec8a94dfd97067bb2a")
//            println("테스트2")
//        }catch (e : Exception){
//            println("인증실패")
//        }

//            tMapTapi.invokeNavigate("T타워", 126.984098f, 37.566385f, 0, true);
//        }
    }
}
