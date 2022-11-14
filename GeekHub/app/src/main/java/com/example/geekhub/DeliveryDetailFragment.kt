package com.example.geekhub

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.geekhub.databinding.FragmentDeliveryDetailBinding

class DeliveryDetailFragment : Fragment() {

    lateinit var binding : FragmentDeliveryDetailBinding
    var title:String? = "title"
    var idx:String? = "idx"
    lateinit var pref : SharedPreferences
    lateinit var userid : String
    var url : String? = "url"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryDetailBinding.inflate(inflater,container,false)
        pref = requireActivity().getSharedPreferences("idKey", 0)
        userid = pref.getString("id", "").toString()
        arguments?.let{
            title = it.getString("title")
            idx = it.getString("idx")
            url = it.getString("url")
            binding.detailTitle.text = title
        }
//        binding.delInfomation.setText("음식 사진을 찍어주세요")
//        Glide.with(requireContext())
//            .load(R.drawable.phone_camera_name) // 불러올 이미지 url
//            .into(binding.cameraImage) // 이미지를 넣을 뷰


        Glide.with(requireContext())
            .load(url) // 불러올 이미지 url
            .into(binding.detailLogoIcon) // 이미지를 넣을 뷰

        binding.detailButton.setOnClickListener{

//            (activity as MainActivity).sendUserId(idx!!,userid)
        }

        return binding.root
    }




}