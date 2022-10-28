package com.example.geekhub

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ACTION_NDEF_DISCOVERED
import android.nfc.NfcAdapter.EXTRA_TAG
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.geekhub.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.reflect.KClass


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NavFragment>(R.id.main_container_view)
            }
        }

        val MY_PERMISSION_ACCESS_ALL = 100

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    ){
            var permissions = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)

        } // 퍼미션
        

        binding.goChatting.setOnClickListener{
            moveFragment(ChattingFragment())
        }





    }


    fun changeFragment(index:Int) {
        when(index){
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
        println("온어뎁터")
        var tagFromIntent: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        val nfc = NfcA.get(tagFromIntent)
        val atqa: ByteArray = nfc.getAtqa()
        val sak: Short = nfc.getSak()
        nfc.connect()
        val isConnected= nfc.isConnected()

        if(isConnected)
        {
            println("연결")
//            val receivedData:ByteArray= nfc.transceive(NFC_READ_COMMAND)
//            ..
//            //code to handle the received data
//            // Received data would be in the form of a byte array that can be converted to string
//            //NFC_READ_COMMAND would be the custom command you would have to send to your NFC Tag in order to read it
//            ..
        } else{
        Log.e("ans", "Not connected")
    }
    }

    fun showMsg(mMessage: NdefMessage) {
        val recs = mMessage.records
        for (i in recs.indices) {
            val record = recs[i]
            if (Arrays.equals(record.type, NdefRecord.RTD_URI)) {
                val u: Uri = record.toUri()
                val j = Intent(Intent.ACTION_VIEW)
                j.data = u
                startActivity(j)
                finish()
            }
        }
    }




















}