package com.example.geekhub

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geekhub.data.DeliveryList
import com.example.geekhub.data.DeliveryResponse
import com.example.geekhub.databinding.ActivityReadyBinding
import com.example.geekhub.databinding.RecyclerReadyListBinding
import com.example.geekhub.retrofit.NetWorkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReadyActivity : AppCompatActivity() {
    lateinit var binding: ActivityReadyBinding
    lateinit var pref : SharedPreferences
    lateinit var userid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("idKey", 0)
        userid = pref.getString("id", "").toString()
        getDeliveryList(userid.toInt())
        println(userid.toInt())

        binding.loadingLogo.visibility = View.VISIBLE
        binding.copytight.visibility = View.VISIBLE
        binding.readyFragmentView.setBackgroundResource(R.color.black)
        binding.deliveryFree.visibility = View.INVISIBLE
    }

    fun getDeliveryList(number : Int){
        val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:9013/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        val call = callData.getlist(number)

        call.enqueue(object : Callback<DeliveryList> {
            override fun onFailure(call: Call<DeliveryList>, t: Throwable) {
                Log.e("안됨",t.toString())
            }

            override fun onResponse(call: Call<DeliveryList>, response: Response<DeliveryList>) {
                println("됨")
                val result = response.body()
                println(response.code())

                if (result!!.isFinished == true){
                    dobby()
                    binding.deliveryFree.setText("오늘의 일을 완료했어요!")
                    binding.loadingLogo.visibility = View.INVISIBLE
                    binding.copytight.visibility = View.INVISIBLE
                    binding.readyFragmentView.setBackgroundResource(R.color.white)

                    return
                }

                if (result!!.del.size + result!!.rec.size == 0){
                    dobby()
                    binding.deliveryFree.setText("오늘은 업무가 없어요 ~_~")

                    return
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@ReadyActivity,MainActivity::class.java))
                    finish()
                }, 3000)


            } })
    }


    fun dobby() {
        binding.deliveryDobby.setImageResource(R.drawable.dobby)
        binding.deliveryFree.visibility = View.VISIBLE
    }
}