package com.example.hessianchatclient

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import api.model.Message
import api.service.CabBookingService
import api.service.ChatService
import com.caucho.hessian.client.HessianProxyFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }


    val hessianFactory: HessianProxyFactory = HessianProxyFactory()
    val chatService =
        hessianFactory.create(ChatService::class.java, HESSIAN_CHAT_URL) as ChatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTestData()

        val testButton = findViewById<Button>(R.id.test_button) as Button
        testButton.setOnClickListener(View.OnClickListener { testUpdatingMessages() })
    }

    private fun setTestData() {
        usernameEditText.setText("Arystoteles") // Hektor Parystokles Arystoteles
        chatIdEditText.setText("62")
    }

    fun startChatActivity(v: View) {
        val intent = ChatActivity.newIntent(
            this,
            usernameEditText.text.toString(),
            chatIdEditText.text.toString()
        )
        startActivity(intent)
    }

    fun sendHessianBookingRequest() {
        GlobalScope.launch {
            ////            val cabBookingService : CabBookingService = hessianFactory.create(CabBookingService::class.java, hessianUrl) as CabBookingService
//            val cabBookingService: CabBookingService = hessianFactory.create(
//                CabBookingService::class.java,
//                hessianUrl, classLoader
//            ) as CabBookingService
//            val string = "Kosmopolit"
//            val output : Booking = cabBookingService.bookRide(string)
//            Log.v(TAG, output.toString())


//            val service: CabBookingService = ProxyFactory.getProxy(
//                CabBookingService::class.java, "booking"
//            )
//            val string = "Skargi 23"
//            val output : Booking = service.bookRide(string)
//            Log.v(TAG, output.toString())

            val factory: HessianProxyFactory = HessianProxyFactory()
//            factory.setHessian2Reply(false)
            val service = factory.create(
                CabBookingService::class.java,
                HESSIAN_BOOKING_URL
            ) as CabBookingService
            val output = service.bookRide("Roweckiego 23")
            Log.v(TAG, output.toString())
        }
    }

    fun testChatService() {
        GlobalScope.launch {
            Log.v(TAG, "testChatService")

            try {

                val message = Message(-1, "Hor", "Omega")
                chatService.postMessage(3L, message)
//            for (i in 1..5) {
//                chatService.postMessage(3L, message)
//            }
//
                val chatRoom = chatService.getAllMessages(3L)
                for (message in chatRoom.messageList) {
                    Log.v(TAG, message.toString())
                }
//                Log.v(TAG, "messages: ${chatRoom.messageList}}")
            } catch (e: Exception) {
                Log.e(TAG, e.localizedMessage)
            }

        }
    }

    fun testUpdatingMessages() {
        GlobalScope.launch {
            val chatRoomId = 6L
            val lastMessageId = 40L
            for (character in 'a'..'m') {
                val message = Message(-1, "Kornelius", character.toString())
                chatService.postMessage(chatRoomId, message)
            }
            val chatRoom = chatService.getAllMessages(chatRoomId)
            Log.v(TAG, "All messages: $chatRoom")
            val newMessages = chatService.getMessagesUpdate(chatRoomId, lastMessageId)
            Log.v(TAG, "Messages from $lastMessageId: $newMessages")
        }
    }
}