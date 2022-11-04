package com.example.geekhub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhub.data.DeliveryResponse
import com.example.geekhub.data.DeliveryList
import com.example.geekhub.databinding.FragmentDeliveryBinding
import com.example.geekhub.databinding.RecyclerDeliveryListBinding
import com.example.geekhub.retrofit.NetWorkInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class DeliveryFragment : Fragment() {
    lateinit var binding : FragmentDeliveryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)
        binding.receiveLine.visibility = View.VISIBLE
        getDeliveryList(0)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.receiveButton.setOnClickListener{
            openReceive()
            getDeliveryList(0)
        }
        binding.deliveryButton.setOnClickListener {
            openDelivery()
            getDeliveryList(1)
        }

    }



    private fun openReceive(){
        binding.receiveLine.visibility = View.VISIBLE
        binding.deliveryLine.visibility = View.INVISIBLE
    }

    private fun openDelivery(){
        binding.receiveLine.visibility = View.INVISIBLE
        binding.deliveryLine.visibility = View.VISIBLE

    }


    fun getDeliveryList(number : Int){
        val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:8012/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        val call = callData.getlist()
        call.enqueue(object : Callback<DeliveryList>{
            override fun onFailure(call: Call<DeliveryList>, t: Throwable) {
                Log.e("안됨",t.toString())
            }

            override fun onResponse(call: Call<DeliveryList>, response: Response<DeliveryList>) {
                println("됨")
                val result = response.body()

                if (number == 0){
                    val datas = result!!.rec
                    selectDeliveryList(datas)

                }else{
                    val datas = result!!.del
                    selectDeliveryList(datas)
                }
            } })
    }

    fun selectDeliveryList(datas: ArrayList<DeliveryResponse>){
        val deliveryListAddapter = DeliveryListAddapter(datas)
        binding.deliveryListRecycler.adapter = deliveryListAddapter
        binding.deliveryListRecycler.layoutManager =  LinearLayoutManager(requireContext())

    }


    inner class DeliveryListAddapter(private var datas : ArrayList<DeliveryResponse>) :
        RecyclerView.Adapter<DeliveryListAddapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = RecyclerDeliveryListBinding.inflate(layoutInflater)
            val holder = ViewHolder(binding)
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                200
            )

            binding.root.layoutParams = layoutParams
            println(datas)
            println("데이타스")



            return holder

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.text = datas[position].spotName
            holder.count.text = datas[position].count
            holder.time.text = datas[position].expected_time

        }

        override fun getItemCount(): Int {
            return datas.size
        }


        inner class ViewHolder(binding: RecyclerDeliveryListBinding)
            : RecyclerView.ViewHolder(binding.root) {
            val title = binding.deliveryListTitle
            val count = binding.deliveryListCount
            val time = binding.deliveryListTime
        }



    }


    }




