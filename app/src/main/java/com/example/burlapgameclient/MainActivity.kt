package com.example.burlapgameclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import api.service.ChatService
import com.caucho.hessian.client.HessianProxyFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
        testButton.setOnClickListener(View.OnClickListener { testBurlap() })
    }

    private fun setTestData() {
        usernameEditText.setText("Achilles") // Hektor Achilles Parystokles Arystoteles
        chatIdEditText.setText("60")
    }

    fun startChatActivity(v: View) {
//        val intent = ChatActivity.newIntent(
//            this,
//            usernameEditText.text.toString(),
//            chatIdEditText.text.toString()
//        )
//        startActivity(intent)
    }

    fun testBurlap() {
        GlobalScope.launch {
            BurlapTest.runSomeTests()
        }
/*        val burlapProxyFactory : BurlapProxyFactory = BurlapProxyFactory()
        val chatService = burlapProxyFactory.create(ChatService::class.java, BURLAP_CHAT_URL) as ChatService
        Log.v(TAG, "Burlap testRigidly: ${chatService.getTestChatString("burlap testRigidly Bioaron")}")*/
    }

}
