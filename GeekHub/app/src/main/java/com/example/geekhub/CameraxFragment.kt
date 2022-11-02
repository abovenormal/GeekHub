package com.example.geekhub

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import androidx.core.graphics.createBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.geekhub.databinding.FragmentCameraxBinding
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias LumaListener = (luma: Double) -> Unit

private var imageCapture: ImageCapture? = null
private lateinit var cameraExecutor: ExecutorService
private const val TAG = "카메라"
private var path = ""
private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val REQUEST_CODE_PERMISSIONS = 10

class CameraxFragment : Fragment() {
    lateinit var binding : FragmentCameraxBinding
    private lateinit var callback: OnBackPressedCallback

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

        return binding.root
    }
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val destPath = requireActivity().externalCacheDir.toString() + "/cache_file.jpg"
        val image = File(destPath)
        image.createNewFile()
        val savedUri = Uri.fromFile(image)
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(image)
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
                    binding.imageViewPreview.setImageURI(savedUri )
                    binding.cameraBackground.setBackgroundColor(getResources().getColor(R.color.white))
                }
            }
        )
        Log.d("여기2?",image.path)
    }


    private fun startCamera() {
        println("여기")
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




}