package com.example.geekhub

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.geekhub.databinding.FragmentCameraxBinding
import com.example.geekhub.retrofit.NetWorkClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias LumaListener = (luma: Double) -> Unit

private var imageCapture: ImageCapture? = null
private lateinit var cameraExecutor: ExecutorService
private const val TAG = "카메라"


class CameraxFragment : Fragment() {
    lateinit var binding : FragmentCameraxBinding
    private lateinit var callback: OnBackPressedCallback
    lateinit var savedUri:Uri
    lateinit var imageFile:File

    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraxBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        startCamera()
        // Set up the listeners for take photo and video capture buttons
        binding.imageCaptureButton.setOnClickListener { takePhoto() }
        cameraExecutor = Executors.newSingleThreadExecutor()
        binding.reTakePicture.setOnClickListener{
            println("체크")
            cameraExecutor.shutdown()
            binding.viewFinder.visibility = View.VISIBLE
            binding.imageCaptureButton.visibility = View.VISIBLE
            binding.cameraButtons.visibility = View.INVISIBLE
            binding.imageViewPreview.visibility = View.INVISIBLE
            binding.cameraBackground.setBackgroundColor(getResources().getColor(R.color.black))
            startCamera()
        }

        binding.sendPicture.setOnClickListener {
            send()
        }

        return binding.root
    }
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val time = System.currentTimeMillis()
        val destPath = requireActivity().externalCacheDir.toString() + "/${time}cache_file.jpg"
        imageFile = File(destPath)
        imageFile.createNewFile()
        savedUri = Uri.fromFile(imageFile)
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(imageFile)
            .build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                }
                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    binding.viewFinder.visibility = View.INVISIBLE
                    binding.imageCaptureButton.visibility = View.INVISIBLE
                    binding.cameraButtons.visibility = View.VISIBLE
                    binding.imageViewPreview.visibility = View.VISIBLE
                    binding.imageViewPreview.setImageURI(savedUri )
                    binding.cameraBackground.setBackgroundColor(getResources().getColor(R.color.white))
                }
            }
        )
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
//                .setTargetAspectRatio(RATIO_4_3)
                .setTargetResolution(Size(960, 960))
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture,imageAnalyzer)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishFragment()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun finishFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().remove(this@CameraxFragment).commit()
        fragmentManager.popBackStack()
        cameraExecutor.shutdown()
    }

    fun send(){
        val body = RequestBody.create(MediaType.parse("image/*"),imageFile)
        val image = MultipartBody.Part.createFormData("image",imageFile.name,body)
        val call = NetWorkClient.GetNetwork.sendimage(image)
        call.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(requireActivity(),"전송을 실패했습니다.",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result: String? =response.body()
                finishFragment()
                (activity as MainActivity).changeFragment(1)

            }
        })}




}

