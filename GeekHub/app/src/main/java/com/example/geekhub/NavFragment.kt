package com.example.geekhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.geekhub.databinding.FragmentNavBinding

class NavFragment : Fragment() {
    lateinit var binding : FragmentNavBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavBinding.inflate(inflater,container,false)

        return binding.root
    }

}