package com.example.geekhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.geekhub.databinding.FragmentDeliveryCameraBinding

class DeliveryCameraFragment : Fragment() {

    lateinit var binding : FragmentDeliveryCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryCameraBinding.inflate(inflater,container,false)

        binding.sendPicture.setOnClickListener{
            (activity as MainActivity).changeFragment(5)

        }
        // Inflate the layout for this fragment
        return binding.root
    }


}