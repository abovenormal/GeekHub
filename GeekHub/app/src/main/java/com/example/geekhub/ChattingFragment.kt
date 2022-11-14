package com.example.geekhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.TextUtils.concat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.geekhub.databinding.FragmentChattingBinding
import org.json.JSONObject
import java.net.Socket


class ChattingFragment : Fragment() {

    lateinit var binding : FragmentChattingBinding
    lateinit var listener : RecognitionListener
    lateinit var pref : SharedPreferences
    lateinit var userid : String
    lateinit var mSocket : Socket
    var mainViewModel = MainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pref = requireActivity().getSharedPreferences("idKey",0)
        userid = pref.getString("id", "").toString()
        // 저장되어있는 id값 가져오기
        binding = FragmentChattingBinding.inflate(inflater,container,false)
        (activity as MainActivity).lockedChat()
        // 채팅 중복파일 막기

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.geekhub")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR")
        setListener() // 음성인식 가져오기
        
        binding.mic.setOnClickListener{
            val mRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
            mRecognizer.setRecognitionListener(listener)
            mRecognizer.startListening((intent))
        } // 음성인식 시작


        mainViewModel.runStomp()
        
        binding.sendButton.setOnClickListener{
            mainViewModel.sendMessage()
        }



        return binding.root
    }




    private fun setListener() {
        listener = object : RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {
                Toast.makeText(context,"음성 인식 시작",Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {
            }

            override fun onBufferReceived(p0: ByteArray?) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(p0: Int) {
            }

            override fun onResults(p0: Bundle?) {
                val blank = " "
                var matches : ArrayList<String> = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)as ArrayList<String>
                println(binding.editChatting.text)
                var word = concat(binding.editChatting.text,blank,matches[0])
                binding.editChatting.setText(word)

            }

            override fun onPartialResults(p0: Bundle?) {
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
            }

            override fun onRmsChanged(p0: Float) {
            }
        }
    } // 음성듣기

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).activeChat() // 다시 채팅아이콘 활성화
    }

}