package com.example.hessianchatclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import api.model.Message
import api.service.ChatService
import com.caucho.hessian.client.HessianProxyFactory
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatActivity : AppCompatActivity() {
    companion object {
        private val TAG = ChatActivity::class.java.simpleName

        private val INTENT_USER_NAME = "INTENT_USER_NAME"
        private val INTENT_CHAT_NAME = "INTENT_CHAT_NAME"

        fun newIntent(context: Context, userName: String, chatId: String) : Intent{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(INTENT_USER_NAME, userName)
            intent.putExtra(INTENT_CHAT_NAME, chatId)
            return intent
        }
    }

    private lateinit var userName : String
    private var chatId : Long = -1
    private var messageList: List<Message> = ArrayList()

    val hessianFactory: HessianProxyFactory = HessianProxyFactory()
    val chatService =
        hessianFactory.create(ChatService::class.java, HESSIAN_CHAT_URL) as ChatService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageList = ArrayList()

        userName = intent.getStringExtra(INTENT_USER_NAME)
        chatId = intent.getStringExtra(INTENT_CHAT_NAME).toLong()

        Toast.makeText(this, "$userName : $chatId", Toast.LENGTH_SHORT).show()

        recyclerview_message_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = ChatRecyclerViewAdapter(messageList)
        }

        loadAllMessages()

        return
    }

    fun generateSampleMessageList() : List<Message> {
        val messageList = ArrayList<Message>()
        messageList.add(Message("Spielberg", "Hello"))
        messageList.add(Message("Parystokles", "Ich bin Vulgaris"))
        messageList.add(Message("Demistoteles", "Magistralis"))
        return messageList
    }

    fun loadAllMessages() {
        GlobalScope.launch {
            val chatRoom = chatService.getAllMessages(chatId)
            messageList = chatRoom.messageList
            (recyclerview_message_list.adapter as ChatRecyclerViewAdapter).setItems(messageList)
            recyclerview_message_list.adapter?.notifyDataSetChanged()
        }
    }

    fun sendMessage(view : View) {
        GlobalScope.launch {
            chatService.postMessage(chatId, Message(userName, message_edit_text.text.toString()))

            message_edit_text.setText("")
//            Toast.makeText(this@ChatActivity, "Message sent", Toast.LENGTH_SHORT).show()
        }
    }
}
