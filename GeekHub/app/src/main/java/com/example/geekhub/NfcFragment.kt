package com.example.geekhub

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Resources
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.geekhub.databinding.FragmentNfcBinding


class NfcFragment : Fragment() {
    lateinit var binding : FragmentNfcBinding
    var title:String? = "s"
    var state = 0

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



    fun getNfc(){

        try{
            var nfcAdapter = NfcAdapter.getDefaultAdapter(requireActivity().applicationContext)

            if(nfcAdapter.isEnabled){
                println("nfc 켜져있어용")
                binding.nfcInfomation1.setText(resources.getText(R.string.nfc_scan))
                binding.nfcInfomation1.setText(resources.getText(R.string.nfc_playing))
                state = 0

            }else{
                if (state == 0){
                    startActivity(Intent(android.provider.Settings.ACTION_NFC_SETTINGS))
                    state += 1
                }
                println("nfc 꺼져있어용")
                binding.nfcInfomation1.setText("아래 버튼을 눌러 nfc를 켜주세요!")
                binding.nfcInfomation2.setText("nfc를 꼭 일반모드로 설정해주세요")
                binding.nfcImage.setOnClickListener{
                    startActivity(Intent(android.provider.Settings.ACTION_NFC_SETTINGS))
                }


            }

        }catch (e: Exception){
            Log.e("NFC미지원 단말기",e.toString())

        }
        
    }

    override fun onResume() {
        super.onResume()
        getNfc()
    }


}