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
import com.bumptech.glide.Glide
import com.example.geekhub.databinding.FragmentNfcBinding
import kotlinx.android.synthetic.main.fragment_delivery.*


class NfcFragment : Fragment() {
    lateinit var binding : FragmentNfcBinding
    var title:String? = "s"
    var state = 0
    var url:String? = "url"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNfcBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        arguments?.let{
            println("체크")
            title = it.getString("title")
            url = it.getString("url")
            println(title)
            binding.nfcTitle.text = title
        }

        binding.nfcInfomation1.setText("아래 버튼을 눌러 nfc를 켜주세요!\nnfc를 꼭 일반모드로 설정해주세요")

        Glide.with(requireContext())
            .load(R.drawable.nfc_animation) // 불러올 이미지 url
            .into(binding.nfcImage) // 이미지를 넣을 뷰

        Glide.with(requireContext())
            .load(url) // 불러올 이미지 url
            .into(binding.logoIcon) // 이미지를 넣을 뷰



        return binding.root
    }



    fun getNfc(){

        try{
            var nfcAdapter = NfcAdapter.getDefaultAdapter(requireActivity().applicationContext)

            if(nfcAdapter.isEnabled){
                println("nfc 켜져있어용")
                binding.nfcInfomation1.setText("스캔 준비가 완료되었습니다.")
                state = 0

            }else{
                if (state == 0){
                    startActivity(Intent(android.provider.Settings.ACTION_NFC_SETTINGS))
                    state += 1
                }
                println("nfc 꺼져있t습")
                binding.nfcInfomation1.setText("NFC가 꺼져있습니다 위 그림을 눌러서 꼭 일반모드로 켜주세요")
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