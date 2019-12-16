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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    val HEROKU_URL = "https://hessian-chat.herokuapp.com/"
    val LOCALHOST_URL = "http:/10.0.2.2:8080/"
    val BASE_URL = LOCALHOST_URL
    val hessianBookingUrl: String = "${BASE_URL}booking"
    val hessianChatUrl: String = "${BASE_URL}chat"
    val hessianFactory: HessianProxyFactory = HessianProxyFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        ProxyFactory.init(classLoader, hessianUrl)

        val testButton = findViewById<Button>(R.id.test_button) as Button
        testButton.setOnClickListener(View.OnClickListener { testChatService() })
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
                hessianBookingUrl
            ) as CabBookingService
            val output = service.bookRide("Roweckiego 23")
            Log.v(TAG, output.toString())
        }
    }

    fun testChatService() {
        GlobalScope.launch {
            Log.v(TAG, "testChatService")

            try {
                val chatService =
                    hessianFactory.create(ChatService::class.java, hessianChatUrl) as ChatService

                val message = Message("Hor", "Omega")
                chatService.postMessage(3L, message)
//            for (i in 1..5) {
//                chatService.postMessage(3L, message)
//            }
//
                val chatRoom = chatService.getAllMessages(3L)
                for(message in chatRoom.messageList) {
                    Log.v(TAG, message.toString())
                }
//                Log.v(TAG, "messages: ${chatRoom.messageList}}")
            } catch (e : Exception) {
                Log.e(TAG, e.localizedMessage)
            }

        }


    }
}
