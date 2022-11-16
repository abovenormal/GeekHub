package com.example.geekhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextUtils.concat
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhub.data.ChattingRoomResponse
import com.example.geekhub.data.DeliveryResponse
import com.example.geekhub.data.Member
import com.example.geekhub.data.messageData
import com.example.geekhub.databinding.FragmentChattingBinding
import com.example.geekhub.databinding.RecyclerChattingBinding
import com.example.geekhub.retrofit.NetWorkInterface
import kotlinx.android.synthetic.main.fragment_chatting.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.net.Socket
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class ChattingFragment : Fragment() {

    lateinit var binding: FragmentChattingBinding
    lateinit var listener: RecognitionListener
    lateinit var pref: SharedPreferences
    lateinit var userid: String
    var ChattingRoomId: String? = null
    var LocalSchool: String? = null
    val url = "ws://k7c205.p.ssafy.io/8088/endpoint/websocket" // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    lateinit var datas: ArrayList<messageData>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = requireActivity().getSharedPreferences("idKey", 0)
        userid = pref.getString("id", "").toString()
        // 저장되어있는 id값 가져오기
        binding = FragmentChattingBinding.inflate(inflater, container, false)
        (activity as MainActivity).lockedChat()
        // 채팅 중복파일 막기
//
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.example.geekhub")
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        setListener() // 음성인식 가져오기

        getChattingRoom(userid)





        binding.chattingBackButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(ChattingFragment()).commit()
            fragmentManager.popBackStack()
        }
        binding.sendButton.setOnClickListener {
            println("얍얍")
            setListener() // 음성인식 가져오기
            val mRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
            mRecognizer.setRecognitionListener(listener)
            mRecognizer.startListening((intent))

        }

        binding.editChatting.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editChatting.text.toString().isNotEmpty()) {
                    binding.sendBirdButton.setImageResource(R.drawable.send)
                    binding.sendBackgroundButton.setBackgroundResource(R.color.gick_blue)
                    binding.sendButton.setOnClickListener {
                        var message = binding.editChatting.text.toString()
                        sendMessage(userid, message)
                        binding.editChatting.setText("")
                    }

                } else {
                    binding.sendBirdButton.setImageResource(R.drawable.mic)
                    binding.sendBackgroundButton.setBackgroundResource(R.color.red)
                    binding.sendButton.setOnClickListener {
                        println("얍얍")
                        val mRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
                        mRecognizer.setRecognitionListener(listener)
                        mRecognizer.startListening((intent))
                    } // 음성인식 시작
                }

            }
        })




        return binding.root
    }


    private fun setListener() {
        listener = object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Toast.makeText(context, "음성 인식 시작", Toast.LENGTH_SHORT).show()
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
                var matches: ArrayList<String> =
                    p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) as ArrayList<String>
                println(binding.editChatting.text)
                var word = concat(binding.editChatting.text, blank, matches[0])
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

    private fun getChattingRoom(userid: String) {
        val retrofit = Retrofit.Builder().baseUrl("https://k7c205.p.ssafy.io:8088/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        var call = callData.findChatRoom(userid)
        call.enqueue(object : Callback<ChattingRoomResponse> {
            override fun onFailure(call: Call<ChattingRoomResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ChattingRoomResponse>,
                response: Response<ChattingRoomResponse>
            ) {
                println(response.code())
                ChattingRoomId = response.body()?._id
                LocalSchool = response.body()?.localSchool

                findMember(LocalSchool!!)
                openStomp(ChattingRoomId!!)
                receiveData(ChattingRoomId!!)
                println(ChattingRoomId)


            }
        })
    }

    private fun findMember(school: String) {
        val retrofit = Retrofit.Builder().baseUrl("https://k7c205.p.ssafy.io/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val callData = retrofit.create(NetWorkInterface::class.java)
        val call = callData.findChatMember(school)
        call.enqueue(object : Callback<List<Member>> {
            override fun onFailure(call: Call<List<Member>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Member>>, response: Response<List<Member>>) {
                println(response.code())
                println("에뤄코르")
                var result = response.body()
                if (result != null) {
                    for (data in result) {
                        println("너의 이름은")
                        println(data.userName)

                    }
                }

            }
        })

    }

    fun openStomp(id: String) {

        stompClient.topic("/chat/${id}").subscribe {
            var message = messageData()


//            addItem(it)
            println("확인")
            println(it)
        }

        stompClient.connect()

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("소OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("소CLOSED", "!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("소CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.i("소ELSE", lifecycleEvent.message)
                    println("확인했니")
                }
            }
        }

    }

    fun sendMessage(user: String, content: String) {
        try {
            val data = JSONObject()
            data.put("sender", user)
            data.put("content", content)
            data.put("roomId", ChattingRoomId)
            stompClient.send("/app/sendMessage", data.toString()).subscribe()


        } catch (e: java.lang.Error) {
            Log.d("에러", e.toString())
        }
    }

    fun receiveData(roomId: String) {
        val retrofit = Retrofit.Builder().baseUrl("https://k7c205.p.ssafy.io:8088/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val callData = retrofit.create(NetWorkInterface::class.java)
        val call = callData.receiveMessage(roomId)
        call.enqueue(object : Callback<ArrayList<messageData>> {
            override fun onFailure(call: Call<ArrayList<messageData>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ArrayList<messageData>>,
                response: Response<ArrayList<messageData>>
            ) {
                val result = response.body()

                println(result)
                binding.chattingRecycler.adapter = ChattingAddapter(result!!)
                binding.chattingRecycler.layoutManager =  LinearLayoutManager(requireContext())

            }
        })
    }


    inner class ChattingAddapter(
        datas: ArrayList<messageData>
    ) :
        RecyclerView.Adapter<ChattingFragment.ChattingAddapter.ViewHolder>() {
        var previewId = ""



        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ChattingFragment.ChattingAddapter.ViewHolder {
            val binding = RecyclerChattingBinding.inflate(layoutInflater)
            val holder = ViewHolder(binding)
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                220
            )

            binding.root.layoutParams = layoutParams

            return holder

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = datas[position]
            println("포지션,데이터사이즈")
            println(position)
            println(datas.size -1)
            if(position == datas.size -1){
                binding.chattingRecycler.scrollToPosition(position)
            }

            if (data.userId == userid){

                previewId = data.userId
                holder.oContent.visibility = View.INVISIBLE
                holder.oNickname.visibility = View.INVISIBLE
                holder.oTime.visibility = View.INVISIBLE

                holder.mTime.visibility = View.VISIBLE
                holder.mContent.visibility = View.VISIBLE

                holder.mTime.setText(data.created_at)
                holder.mContent.setText(data.content)
            }else{
                if( previewId != data.userId){
                    holder.oNickname.setText(data.name)
                    previewId = data.userId

                }
                holder.oContent.setText(data.content)
                holder.oTime.setText(data.created_at)
            }

            val smoothScroller: RecyclerView.SmoothScroller by lazy {
                object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference() = SNAP_TO_START
                }
            }
            smoothScroller.targetPosition = position
            chatting_recycler.layoutManager?.startSmoothScroll(smoothScroller)




        }

        override fun getItemCount(): Int {
            return datas.size
        }



        inner class ViewHolder(binding: RecyclerChattingBinding)
            : RecyclerView.ViewHolder(binding.root) {
            val oNickname = binding.otherNickname
            val oContent = binding.otherNicknameContent
            val oTime = binding.otherNicknameTime

            val mContent = binding.myNicknameContent
            val mTime = binding.myNicknameTime


        }

    }

    fun addItem(message:messageData) {
        datas.add(message)

    }


}