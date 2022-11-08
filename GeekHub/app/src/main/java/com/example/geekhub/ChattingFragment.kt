package com.example.geekhub

import android.content.Intent
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
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.reflect.typeOf

class ChattingFragment : Fragment() {

    lateinit var binding : FragmentChattingBinding
    lateinit var listener : RecognitionListener
    lateinit var client : OkHttpClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBinding.inflate(inflater,container,false)
        binding.adminChatting.visibility = View.VISIBLE

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.geekhub")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR")

        setListener()

        binding.mic.setOnClickListener{
            val mRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
            mRecognizer.setRecognitionListener(listener)
            mRecognizer.startListening((intent))
        }
        client = OkHttpClient()

        val request : Request = Request.Builder()
            .url("http:k7c205.p.ssafy.io:8088/chat/test")
            .build()
        val listener: WebSocketListener = WebSocketListener()

        client.newWebSocket(request, listener)
        client.dispatcher().executorService().shutdown()

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        setSpinner(binding.spinnerTime,arrayOf("저녁1","저녁2"))
        setSpinner(binding.spinnerLocation,arrayOf("알촌1동","알촌2동"))
        spinnerHandler(binding.spinnerTime)
        spinnerHandler(binding.spinnerLocation)

        binding.adminTitle.setOnClickListener{
            openAdmin()
        }
        binding.colleagueTitle.setOnClickListener{
            openColleague()
        }

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
                println("됨")
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
    }

    private fun setSpinner (a:Spinner,b:Array<String>) {
        val adapter = ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,b)
        a.adapter = adapter
    }

    private fun spinnerHandler (a: Spinner) {
        a.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun openAdmin () {
       binding.adminChatting.visibility = View.VISIBLE
       binding.colleagueChatting.visibility = View.INVISIBLE
    }

    private fun openColleague () {
        binding.colleagueChatting.visibility = View.VISIBLE
        binding.adminChatting.visibility = View.INVISIBLE
    }





}