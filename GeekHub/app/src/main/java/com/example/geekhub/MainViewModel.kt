package com.example.geekhub
import android.util.Log
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent


class MainViewModel : ViewModel() {

    val url = "ws://k7c205.p.ssafy.io:8088/endpoint/websocket" // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    val stompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    fun runStomp(){


        stompClient.topic("/chat/group").subscribe {
            println("소체크")
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
                else ->{
                    Log.i("소ELSE", lifecycleEvent.message)
                }
            }
        }


    }

    fun sendMessage(){
        try {
            val data = JSONObject()
            data.put("sender", "1")
            data.put("timestamp","")
            data.put("content", "test")
            data.put("roomId", "CHAT")
            stompClient.send("/app/chat/publish", data.toString()).subscribe()
            println("보냄")
            println(data)
        }catch (e:java.lang.Error){
            Log.d("에러",e.toString())
        }
    }
}