package com.example.geekhub

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.core.content.FileProvider
import androidx.fragment.app.*
import com.example.geekhub.databinding.FragmentDeliveryCameraBinding
import java.io.File
import java.io.FileOutputStream
import java.net.URI

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



//    fun dispatchTakePictureIntent() {
//        var photoURI: Uri? = null
//        try {
//
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            // 이미지 캡쳐를 인텐트 해옴
//
////            var imageFile = File(MediaStore.Images.Media.RELATIVE_PATH)
////            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
//
//            resultLauncher = registerForActivityResult(
//                ActivityResultContracts.StartActivityForResult()) {
//                if (it.resultCode == RESULT_OK) {
//                    //callback함수로 it 가져오기
//                    val bitmap = it.data?.extras?.get("data") as Bitmap
//                    //비트맵으로 컨버젼
//                    createImage(bitmap)
//
//                    binding.cookImage.setImageBitmap(bitmap)
//                }
//            }
//
//            resultLauncher.launch(intent)
//
//
//        } catch (e: ActivityNotFoundException) {
//        }
//    }
//    fun createImageUri(filename: String, mimeType: String) : Uri? {
//        var values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
//        return requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//    }
//
//    fun createImage(bitmap: Bitmap) {
//        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png
//
//        /*
//        * ContentValues() 객체 생성.
//        * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
//        * */
//        val contentValues = ContentValues()
//        contentValues.apply {
//            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
//            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
//            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
//            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
//            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
//        }
//
//        // 이미지를 저장할 uri를 미리 설정해놓는다.
//        val uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//
//
//            if(uri != null) {
//                val image = requireActivity().contentResolver.openFileDescriptor(uri, "w", null)
//                // write 모드로 file을 open한다.
//
//                if(image != null) {
//                    val fos = FileOutputStream(image.fileDescriptor)
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//                    //비트맵을 FileOutputStream를 통해 compress한다.
//                    fos.close()
//
//                    contentValues.clear()
//                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
//                    requireActivity().contentResolver.update(uri, contentValues, null, null)
//                }
//            }
//
//
//
//
//
//        val cachePath = File(context?.cacheDir, "images")
//        cachePath.mkdirs()
////        val imageFile = File(Activity.getExternalFilesDir(null), ".jpg")
//        val stream = FileOutputStream("${cachePath}/image.png")
//
//
//        //https://stackoverflow.com/questions/9049143/android-share-intent-for-a-bitmap-is-it-possible-not-to-save-it-prior-sharing?rq=1
//
//
//
//    }






}