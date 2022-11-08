package com.example.geekhub

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.geekhub.data.NextSpotInfo
import com.example.geekhub.databinding.FragmentNavBinding
import com.example.geekhub.retrofit.NetWorkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NavFragment : Fragment() {
    lateinit var binding : FragmentNavBinding
    var spot : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pref = requireActivity().getSharedPreferences("idKey", 0)
        var userid = pref.getString("id", "").toString()
        binding = FragmentNavBinding.inflate(inflater,container,false)

        binding.main.setOnClickListener {
            (activity as MainActivity).changeFragment(1)
        }








        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val pref = requireActivity().getSharedPreferences("idKey", 0)
        var userid = pref.getString("id", "").toString()
        nextSpot(userid)
    }

    fun nextSpot(userid: String) {
        val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        val call = callData.nextWork(userid.toInt())
        call.enqueue(object : Callback<NextSpotInfo> {
            override fun onFailure(call: Call<NextSpotInfo>, t: Throwable) {
                Log.e("에러났다", t.toString())
            }

            override fun onResponse(call: Call<NextSpotInfo>, response: Response<NextSpotInfo>) {
                println("여기" + response.body()?.spotName)
                spot = response.body()?.spotName
                binding.spotNav.setText("다음 목적지는 ${spot.toString()}입니다")


            }
        })
    }








}