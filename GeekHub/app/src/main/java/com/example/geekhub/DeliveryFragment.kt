package com.example.geekhub

import android.app.Activity
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.camera2.interop.CaptureRequestOptions
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geekhub.data.DeliveryList
import com.example.geekhub.data.DeliveryResponse
import com.example.geekhub.data.NextSpotInfo
import com.example.geekhub.databinding.FragmentDeliveryBinding
import com.example.geekhub.databinding.RecyclerDeliveryListBinding
import com.example.geekhub.retrofit.NetWorkInterface
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import java.security.cert.PKIXRevocationChecker.Option


class DeliveryFragment : Fragment() {
    lateinit var binding : FragmentDeliveryBinding
    var nowState = 0
    lateinit var userid :String
    var spot : String? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pref = requireActivity().getSharedPreferences("idKey",0)
        userid = pref.getString("id", "").toString()
        Log.d("체크입니다",userid)
        nextSpot(userid)
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)
        if (nowState == 0 ){
//            binding.receiveLine.visibility = View.VISIBLE
            getDeliveryList(0)
        }
        else{
//            binding.deliveryLine.visibility = View.VISIBLE
            getDeliveryList(1)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.receiveButton.setOnClickListener{
//            openReceive()
            getDeliveryList(0)
            nowState = 0
        }
        binding.deliveryButton.setOnClickListener {
//            openDelivery()
            getDeliveryList(1)
            nowState = 1
        }

    }



//    private fun openReceive(){
//        binding.receiveLine.visibility = View.VISIBLE
//        binding.deliveryLine.visibility = View.INVISIBLE
//    }
//
//    private fun openDelivery(){
//        binding.receiveLine.visibility = View.INVISIBLE
//        binding.deliveryLine.visibility = View.VISIBLE
//
//    }


    fun getDeliveryList(number : Int){
        if (number == 0){
            binding.receiveLine.visibility = View.VISIBLE
            binding.deliveryLine.visibility = View.INVISIBLE
            binding.deliveryRec.setTextColor(resources.getColor(R.color.black))
            binding.deliveryRecTitle.setTextColor(resources.getColor(R.color.black))
            binding.deliveryDel.setTextColor(resources.getColor(R.color.gray_500))
            binding.deliveryDelTitle.setTextColor(resources.getColor(R.color.gray_500))

        }
        else{
            binding.receiveLine.visibility = View.INVISIBLE
            binding.deliveryLine.visibility = View.VISIBLE
            binding.deliveryRec.setTextColor(resources.getColor(R.color.gray_500))
            binding.deliveryRecTitle.setTextColor(resources.getColor(R.color.gray_500))
            binding.deliveryDel.setTextColor(resources.getColor(R.color.black))
            binding.deliveryDelTitle.setTextColor(resources.getColor(R.color.black))
        }
        val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:9013/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)

        val call = callData.getlist(userid.toInt())
        println("콜")
        println(call)
        call.enqueue(object : Callback<DeliveryList>{
            override fun onFailure(call: Call<DeliveryList>, t: Throwable) {
                Log.e("안됨",t.toString())
            }

            override fun onResponse(call: Call<DeliveryList>, response: Response<DeliveryList>) {
                println("됨")
                val result = response.body()
                println(result)

                val deldatas : ArrayList<DeliveryResponse> = ArrayList()
                var recdatas : ArrayList<DeliveryResponse> = ArrayList()
                if (result == null){
                    return
                }
                for (i in result!!.rec){
                    if(i.status !=0){
                        recdatas.add(i)
                    }
                }

                for (i in result!!.del){
                    if(i.status !=0){
                        deldatas.add(i)
                    }
                }

                println("데이터파싱")
                println(recdatas)

                binding.deliveryRec.text = "${recdatas.size}개"
                binding.deliveryDel.text = "${deldatas.size}개"

                if (number == 0){
                    selectDeliveryList(recdatas)

                }else{
                    selectDeliveryList(deldatas)
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
                280
            )

            binding.root.layoutParams = layoutParams

            return holder

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // status 0 : 내용없음  1 : 배달해야함    2 : 완료
            val number = datas[position]


            if(number.status == 1){
                visibleList(number,holder)
                holder.title.setTextColor(resources.getColor(R.color.gick_blue))
                holder.count.setTextColor(resources.getColor(R.color.gick_blue))
                holder.time.setTextColor(resources.getColor(R.color.gick_blue))
                holder.main.setOnClickListener{
                    if (nowState == 0){
                        (activity as MainActivity).sendData(NfcFragment(),number.spotName,number.spotIndex)
                    }
                    else{
                        (activity as MainActivity).sendData(DeliveryDetailFragment(),number.spotName,number.spotIndex)
                    }
                }

            } else{
                visibleList(number,holder)
                holder.title.setTextColor(resources.getColor(R.color.gray_500))
                holder.count.setTextColor(resources.getColor(R.color.gray_500))
                holder.time.setTextColor(resources.getColor(R.color.gray_500))
            }

            if(number.spotName == spot){
                holder.title.setTextColor(resources.getColor(R.color.dark_red))
                holder.count.setTextColor(resources.getColor(R.color.dark_red))
                holder.time.setTextColor(resources.getColor(R.color.dark_red))
            }


        }
        fun visibleList(number: DeliveryResponse,holder:ViewHolder){
            holder.title.text = number.spotName
            holder.count.text =  "수량 : ${number.count}개"
            holder.time.text = number.expectedTime
            var ur1 = number.iconUrl
//            val url ="https://geekhub.s3.ap-northeast-2.amazonaws.com/logo/burgerkingLogo.png"

//            var requestOption :Option = Option.cir

            Glide.with(requireActivity())
                .load(ur1) // 불러올 이미지 url
                .placeholder(R.drawable.loading) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(R.drawable.loading) // 로딩 에러 발생 시 표시할 이미지
                .fallback(R.drawable.loading) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(holder.image) // 이미지를 넣을 뷰





            println("빰")
            println(number.expectedTime)
            holder.title.isSelected = true
        }

        override fun getItemCount(): Int {
            return datas.size
        }


        inner class ViewHolder(binding: RecyclerDeliveryListBinding)
            : RecyclerView.ViewHolder(binding.root) {
            val title = binding.deliveryListTitle
            val count = binding.deliveryListCount
            val time = binding.deliveryListTime
            val main = binding.deliveryRecyclerView
            val image = binding.logoImage

        } }

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



            }
        })
    }



}




