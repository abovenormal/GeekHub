package com.example.geekhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geekhub.databinding.FragmentDeliveryDetailBinding

class DeliveryDetailFragment : Fragment() {

     lateinit var binding : FragmentDeliveryDetailBinding
     var title:String? = "s"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryDetailBinding.inflate(inflater,container,false)

        arguments?.let{
            title = it.getString("title")
            binding.detailTitle.text = title
        }

        binding.detailButton.setOnClickListener{
            (activity as MainActivity).changeFragment(6)
        }

        return binding.root
    }




}