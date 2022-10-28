package com.example.geekhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhub.databinding.FragmentDeliveryBinding
import com.example.geekhub.databinding.RecyclerDeliveryListBinding

class DeliveryFragment : Fragment() {
    lateinit var binding : FragmentDeliveryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)

        binding.receiveLine.visibility = View.VISIBLE

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.receiveButton.setOnClickListener{
            openReceive()
        }
        binding.deliveryButton.setOnClickListener {
            openDelivery()
        }

        binding.testComponents.setOnClickListener {
            (activity as MainActivity).changeFragment(2)
        } // 임시


    }



    private fun openReceive(){
        binding.receiveLine.visibility = View.VISIBLE
        binding.deliveryLine.visibility = View.INVISIBLE
    }

    private fun openDelivery(){
        binding.receiveLine.visibility = View.INVISIBLE
        binding.deliveryLine.visibility = View.VISIBLE

    }

//    private fun recycleFragment(){
//
//
//
//    } //recycler view와 연결 예정

}


