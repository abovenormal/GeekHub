package com.example.geekhub

import android.util.Log
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

class MainViewModel : ViewModel() {

    // val url = "http://example.com:8080/"
    val url = "ws://k7c205.p.ssafy.io:8088/socket/websocket" // 소켓에 연결하는 엔드포인트가 /socket일때 다음과 같음
    val stompClient =  Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

    fun runStomp(){

        stompClient.topic("/topic/message/test0912").subscribe { topicMessage ->
            Log.i("message Recieve", topicMessage.payload)
        }

        val headerList = arrayListOf<StompHeader>()
        headerList.add(StompHeader("inviteCode","test0912"))
        headerList.add(StompHeader("username", "test"))
        headerList.add(StompHeader("positionType", "1"))
        stompClient.connect(headerList)

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i("OPEND", "!!")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.i("CLOSED", "!!")

                }
                LifecycleEvent.Type.ERROR -> {
                    Log.i("ERROR", "!!")
                    Log.e("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else ->{
                    Log.i("ELSE", lifecycleEvent.message)
                }
            }
        }

        val data = JSONObject()
//        data.put("userKey", text.value)
        data.put("positionType", "1")
        data.put("content", "test")
        data.put("messageType", "CHAT")
        data.put("destRoomCode", "test0912")

        stompClient.send("/stream/chat/send", data.toString()).subscribe()
    }
}