package com.example.geekhub
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.*
import com.example.geekhub.databinding.FragmentDeliveryCameraBinding


class DeliveryCameraFragment : Fragment() {

    lateinit var binding : FragmentDeliveryCameraBinding
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey"){
            requestKey, bundle ->
            val result = bundle.getString("bundleKey")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryCameraBinding.inflate(inflater,container,false)

        binding.sendPicture.setOnClickListener{
            (activity as MainActivity).changeFragment(5)
        }


        binding.reTakePicture.setOnClickListener(){
            (activity as MainActivity).changeFragment(6)
        }
        if(uri != null){
            binding.cookImage.setImageURI(uri)
        }




        // Inflate the layout for this fragment
        return binding.root
    }



}