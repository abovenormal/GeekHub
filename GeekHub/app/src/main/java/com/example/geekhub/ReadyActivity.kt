package com.example.geekhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = getSharedPreferences("idKey", 0)
        var userid = pref.getString("id", "").toString()
        getDeliveryList(userid.toInt())
        println(userid.toInt())

        binding.deliveryFree.visibility = View.INVISIBLE
        binding.readyTitleView.visibility = View.VISIBLE
        binding.deliveryStartButton.visibility = View.VISIBLE



        binding.deliveryStartButton.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }


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
                if (result == null){
                    return
                }
                if (result!!.del.size + result!!.rec.size == 0){
                    binding.deliveryDobby.setImageResource(R.drawable.dobby)
                    binding.deliveryFree.visibility = View.VISIBLE
                    binding.readyTitleView.visibility = View.INVISIBLE
                    binding.deliveryStartButton.visibility = View.INVISIBLE

                    return
                }


                val readyDatas : ArrayList<DeliveryResponse> = ArrayList()

                val startRespons = DeliveryResponse()
                startRespons.spotName = "업무시작"
                startRespons.status = 4

                readyDatas.add(startRespons)



                for (i in result!!.rec){
                        readyDatas.add(i)
                }

                for (i in result!!.del){
                        readyDatas.add(i)
                }

                val finalRespons = DeliveryResponse()
                finalRespons.spotName = "배달완료!"
                finalRespons.status = 5
                readyDatas.add(finalRespons)
                
                for (i in readyDatas){
                    println(i.spotName)
                } // 업무시작과 업무완료를 포함한 리스트 생성

                selectDeliveryList(readyDatas)




            } })
    }


    fun selectDeliveryList(datas: ArrayList<DeliveryResponse>){

        try {
            val deliveryListAddapter = DeliveryListAddapter(datas)
            binding.recyclerReady.adapter = deliveryListAddapter
            binding.recyclerReady.layoutManager =  GridLayoutManager(this,4)
        }catch(e:java.lang.Exception){
        }

    }


    inner class DeliveryListAddapter(private var datas : ArrayList<DeliveryResponse>) :
        RecyclerView.Adapter<DeliveryListAddapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = RecyclerReadyListBinding.inflate(layoutInflater)
//            val binding = RecyclerView
////            val binding = RecyclerView.RecyclerReadyListBinding.inflate(layoutInflater)
            val holder = ViewHolder(binding)
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                280
            )

            binding.root.layoutParams = layoutParams

            return holder

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // status 0 : 내용없음  1 : 배달해야함    2 : 완료
            println("뷰뷰")
            println(datas[position].status)
//            if(datas[position].status != 0){
            val number = datas[position]
            holder.title.text = number.spotName
            holder.title.isSelected = true
            println(number.status)

            if(number.status == 0){
                println("손!")
                holder.image.setImageResource(R.drawable.stop_icon)
            }
            else if (number.status == 4 ) {
                holder.image.setImageResource(R.drawable.start_del)
                holder.image.setColorFilter(R.color.gick_blue)
                println("짜잔출발")
            }else if (number.status == 5){
                holder.image.setImageResource(R.drawable.finish_del)
                holder.image.setColorFilter(R.color.gick_blue)
                println("짜잔도착")
            }else{
                holder.time.text = number.expected_time
                holder.image.setImageResource(R.drawable.go_arrow)
                holder.image.setColorFilter(R.color.gick_blue)
            }


        }


        override fun getItemCount(): Int {
            return datas.size
        }


        inner class ViewHolder(binding: RecyclerReadyListBinding)
            : RecyclerView.ViewHolder(binding.root) {
            val title = binding.readyDeliveryTitle
            val image = binding.iconView
            val time = binding.readyDeliveryTime

        } }
}