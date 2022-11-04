//package com.example.geekhub
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.geekhub.data.DeliveryDeliveryResponse
//import com.example.geekhub.data.DeliveryRequestResponse
//import com.example.geekhub.databinding.RecyclerDeliveryListBinding
//
//
//
//
//
//class DeliveryListAddapter :
//    RecyclerView.Adapter<DeliveryListAddapter.ViewHolder>(){
//    var datas = ArrayList<DeliveryDeliveryResponse>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
////        val binding = RecyclerDeliveryListBinding.inflate(lah)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//
//    inner class ViewHolder(binding:RecyclerDeliveryListBinding)
//        : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
//        override fun onClick(p0: View?) {
//            // 이후 온클릭 구현
//        }
//
//
//        }
//    }
//
////        var spotName : TextView = itemView.findViewById(R.id.delivery_list_title)
////        var count : TextView = itemView.findViewById(R.id.delivery_list_count)
////        var expected_time : TextView = itemView.findViewById(R.id.delivery_list_time)
////
////        fun bind(item: DeliveryDeliveryResponse) {
////            spotName.text = item.spotName
////            count.text = item.count
////            expected_time.text = item.expected_time
//
//
////            Glide.with(itemView).load("http://i7c207.p.ssafy.io:8080/harufilm/upload/profile/" + item.userimg).into(userimg)
//
////            itemView.setOnClickListener {
////                itemClickListener.onClick(it, item.userpid.toString())
////            }
//
////    // Create new views (invoked by the layout manager)
////    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
////        val binding = RecyclerDeliveryListBinding.inflate(infl)
////
////
//////        val view = LayoutInflater.from(context)
//////            .inflate(R.layout.recycler_delivery_list, viewGroup, false)
//////
//////        return ViewHolder(view)
////    }
////
////    // Replace the contents of a view (invoked by the layout manager)
////    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
////        viewHolder.bind(datas[position])
////    }
////
////    // Return the size of your dataset (invoked by the layout manager)
////    override fun getItemCount(): Int = datas.size
//
////}