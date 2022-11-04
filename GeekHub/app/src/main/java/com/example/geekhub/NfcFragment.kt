package com.example.geekhub

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geekhub.databinding.FragmentNfcBinding


class NfcFragment : Fragment() {
    lateinit var binding : FragmentNfcBinding
    var title:String? = "s"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNfcBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment


        arguments?.let{
            println("체크")
            title = it.getString("title")
            println(title)
            binding.nfcTitle.text = title
        }





        return binding.root
    }




}