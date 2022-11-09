package com.example.geekhub

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.net.Uri
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcF
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.geekhub.data.LocationInfo
import com.example.geekhub.data.NextSpotInfo
import com.example.geekhub.databinding.ActivityMainBinding
import com.example.geekhub.retrofit.NetWorkInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.skt.Tmap.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), TMapGpsManager.onLocationChangedCallback {

    private lateinit var binding: ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var tMapView: TMapView
    private lateinit var tMapTapi: TMapTapi
    private lateinit var tMapPointStart: TMapPoint
    private lateinit var tMapPointEnd: TMapPoint
    private lateinit var bitmap: Bitmap
    private var cnt = 0
    private var gps : TMapGpsManager? = null
    private var nextSpotInfo : NextSpotInfo? = null
    val bundle = Bundle()
    lateinit var userid: String
    var focusStatus = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        focusStatus = 0
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = getSharedPreferences("idKey", 0)
        var userid = pref.getString("id", "").toString()
        next(userid)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NavFragment>(R.id.main_container_view)
//                add<CameraxFragment>(R.id.camera_view)
            }
        }

        val MY_PERMISSION_ACCESS_ALL = 100

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            var permissions = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)
        } // 퍼미션

        initialize(userid)
        val permissionCheck : Int = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0);
        }
        gps = TMapGpsManager(this)
        gps!!.minTime = 5000
        gps!!.minDistance = 0F
        gps!!.provider = TMapGpsManager.GPS_PROVIDER
        gps!!.OpenGps()
        gps!!.provider = TMapGpsManager.NETWORK_PROVIDER
        gps!!.OpenGps()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
                location : Location? ->
            if (location != null) {
                tMapPointStart = TMapPoint(location.latitude, location.longitude)
                println("시작점 찍었다.")
                tMapView.setCenterPoint(tMapPointStart.longitude, tMapPointStart.latitude)
            }
        }


        binding.goChatting.setOnClickListener {
            moveFragment(ChattingFragment())
        }
        // 채팅버튼
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        //nfc
    }

    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        cnt = 0
        val intent: Intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        var pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")    /* Handles all MIME based dispatches.
                                     You should specify only the ones that you need. */
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }

        binding.navButton.setOnClickListener {
            goNav()
        }

        binding.focusButton.setOnClickListener {
            if (focusStatus == 0){
                binding.focusButton.setImageResource(R.drawable.focus_on)
                focusStatus = 1
            } else{
                binding.focusButton.setImageResource(R.drawable.focus_off)
                focusStatus = 0
            }

        }

        var intentFiltersArray = arrayOf(ndef)
        var techListsArray = arrayOf(arrayOf<String>(NfcF::class.java.name))
        nfcAdapter!!.enableForegroundDispatch(
            this,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
        // nfc
    }

//    public override fun onNewIntent(intent: Intent) {
//        val tagFromIntent: Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
//        //do something with tagFromIntent
//    }

    private fun initialize(userid: String) {
        tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("l7xx9f33576ed47042d3ac571c8a70c73e31")
        val linearLayoutTmap: LinearLayout =
            findViewById<LinearLayout>(R.id.linearLayoutTmap)
        linearLayoutTmap.addView(tMapView)
        println("이니셜라이즈")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onLocationChange(location: Location?) {
        println("cnt 확인하기" + cnt)
        if (location != null) {
            sendLocation()
            addMarker()
            tMapPointStart = gps!!.location
//            tMapView.setLocationPoint(tMapPointStart.longitude, tMapPointStart.latitude)
//            tMapView.setIconVisibility(true)
            if (focusStatus == 1){
                tMapView.setCenterPoint(tMapPointStart.longitude, tMapPointStart.latitude)
            }

            if (cnt == 0) {
                findPath()
                cnt += 1
            }
        }
    }


    fun changeFragment(index: Int) {
        when (index) {
            1 -> {
                moveFragment(DeliveryFragment())
            }

            2 -> {
                moveFragment(DeliveryDetailFragment())
            }

            3 -> {
                moveFragment(NfcFragment())
            }

            4 -> {
                moveFragment(DeliveryCameraFragment())
            }

            5 -> {
                clearBackStack()
                moveFragment(DeliveryFragment())
            }
            6 -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CameraxFragment>(R.id.camera_view)
                }
            }
        }
    }

    private fun moveFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container_view, fragment)
            .addToBackStack(null)
            .commit()

    }


    fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val pref = getSharedPreferences("idKey", 0)
        var userid = pref.getString("id", "").toString()
        val tagFromIntent: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (tagFromIntent != null) {
            val data = Ndef.get(tagFromIntent)
            data.connect()
            val message = data.ndefMessage
            val record = message.records
            for (records in record) {
                println(records.payload)
                val convert = String(records.payload, StandardCharsets.UTF_8)
                println("프린트값")
                var spot = convert.substring(3)
                sendUserId(spot,userid)
            }

        }

    }

    fun findPath() {
        tMapPointEnd = TMapPoint(nextSpotInfo!!.lat, nextSpotInfo!!.lon)
        println("길찾기")
        thread {
            try {
                var tMapPolyLine: TMapPolyLine = TMapData().findPathDataWithType(
                    TMapData.TMapPathType.CAR_PATH,
                    TMapPoint(gps!!.location.latitude, gps!!.location.longitude),
                    tMapPointEnd
                )
                tMapPolyLine.lineColor = Color.BLUE
                tMapPolyLine.setLineWidth(2F)
                tMapView.addTMapPolyLine("Line1", tMapPolyLine)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun goNav() {
        tMapTapi = TMapTapi(this)
        if (tMapTapi.isTmapApplicationInstalled) {
            tMapTapi.invokeRoute(nextSpotInfo!!.spotName, nextSpotInfo!!.lon.toFloat(),
                nextSpotInfo!!.lat.toFloat()
            )
        } else {
            var uri = Uri.parse(tMapTapi.tMapDownUrl[0])
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }


    fun sendData(fragment: Fragment, title: String,idx: String) {
        bundle.putString("title", title)
        bundle.putString("idx",idx)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun sendUserId(spot: String,userid:String) {
        val fragment = CameraxFragment()
        Log.d("스트링",spot)
        bundle.putString("spot", spot)
        bundle.putString("userid",userid)
        println("유저아이디체크")
        println(userid)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.camera_view,fragment).commit()
    }

    private fun addMarker() {
        var markerItem : TMapMarkerItem = TMapMarkerItem()
        markerItem.tMapPoint = tMapPointStart
        markerItem.name = "현위치"
//        markerItem.visible = TMapMarkerItem.VISIBLE
        bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.pin_r_m_1)
        markerItem.icon = bitmap
        markerItem.setPosition(0F, 0F)
        tMapView.addMarkerItem("현위치", markerItem)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun sendLocation() {
        val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        val locationBody = LocationInfo()
        locationBody.driver = "1"
        locationBody.longitude = tMapPointStart.longitude.toString()
        locationBody.latitude = tMapPointStart.latitude.toString()
        locationBody.timestamp = getTime()

        val call = callData.sendLocationLog(locationBody)
        call.enqueue(object : Callback<String?>{
            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.e("에러났다", t.toString())
            }

            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                println("에러 안남")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTime() : String {
        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd.HH:mm", Locale.KOREA).format(now)
        val date : String = simpleDateFormat
        return date
    }


    fun next(userid: String) {
        val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        val call = callData.nextWork(userid.toInt())
        call.enqueue(object : Callback<NextSpotInfo>{
            override fun onFailure(call: Call<NextSpotInfo>, t: Throwable) {
                Log.e("에러났다", t.toString())
            }

            override fun onResponse(call: Call<NextSpotInfo>, response: Response<NextSpotInfo>) {
                nextSpotInfo = response.body()
                tMapPointEnd = TMapPoint(response.body()!!.lat, response.body()!!.lon)
                println("END 담았다.")
            }
        })
    }
}


