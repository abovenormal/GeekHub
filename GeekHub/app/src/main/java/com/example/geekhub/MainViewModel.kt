package com.example.geekhub
import android.util.Log
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent


class MainViewModel : ViewModel() {

    val url = "ws://k7c205.p.ssafy.io:8088/endpoint/websocket" // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    val stompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    fun runStomp(id:String){


        stompClient.topic("/chat/${id}").subscribe {
        }

        stompClient.connect()



        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                }
                LifecycleEvent.Type.CLOSED -> {
                    stompClient.disconnect()

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.e("소CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else ->{
                    Log.i("소ELSE", lifecycleEvent.message)
                }
            }
        }


    }

    fun sendMessage(user:String,content:String ){
        try {
            val data = JSONObject()
            data.put("sender", user)
            data.put("content", content)
            data.put("roomId", "")
            stompClient.send("/app/sendMessage", data.toString()).subscribe()
        }catch (e:java.lang.Error){
            Log.d("에러",e.toString())
        }
    }


}