package com.example.geekhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geekhub.databinding.FragmentDeliveryDetailBinding

class DeliveryDetailFragment : Fragment() {

    lateinit var binding : FragmentDeliveryDetailBinding
    var title:String? = "title"
    var idx:String? = "idx"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryDetailBinding.inflate(inflater,container,false)
        val pref = requireActivity().getSharedPreferences("idKey", 0)
        var userid = pref.getString("id", "").toString()
        arguments?.let{6
            title = it.getString("title")
            idx = it.getString("idx")
            binding.detailTitle.text = title
        }

        binding.detailButton.setOnClickListener{

            (activity as MainActivity).sendUserId(idx!!,userid)
        }

        return binding.root
    }




}