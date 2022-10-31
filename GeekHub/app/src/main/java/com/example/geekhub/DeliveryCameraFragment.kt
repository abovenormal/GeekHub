package com.example.geekhub

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import com.example.geekhub.databinding.FragmentDeliveryCameraBinding
import java.net.URI

class DeliveryCameraFragment : Fragment() {

    var startPicture = true
    lateinit var binding : FragmentDeliveryCameraBinding
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryCameraBinding.inflate(inflater,container,false)

        binding.sendPicture.setOnClickListener{
            (activity as MainActivity).changeFragment(5)

        }
        if(startPicture){
            dispatchTakePictureIntent()
            println("왔나")
        }

        // Inflate the layout for this fragment
        return binding.root
    }



    val REQUEST_IMAGE_CAPTURE = 1

    fun dispatchTakePictureIntent() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val bitmap = it.data?.extras?.get("data") as Bitmap
                    binding.cookImage.setImageBitmap(bitmap)
                    startPicture = false
                }
            }

            resultLauncher.launch(intent)





//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            println("체크")


        } catch (e: ActivityNotFoundException) {
        }
    }





}