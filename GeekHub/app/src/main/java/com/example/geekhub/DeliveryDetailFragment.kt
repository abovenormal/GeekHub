package com.example.geekhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geekhub.databinding.FragmentDeliveryDetailBinding

class DeliveryDetailFragment : Fragment() {

     lateinit var binding : FragmentDeliveryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryDetailBinding.inflate(inflater,container,false)


        if (binding.detailButton.text == "NFC 태깅하기"){
            binding.detailButton.setOnClickListener{
                (activity as MainActivity).changeFragment(3)
        } }else{
            binding.detailButton.setOnClickListener{
                (activity as MainActivity).changeFragment(4)}
        }




        return binding.root
    }




}