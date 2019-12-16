package com.example.hessianchatclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        fun newIntent(context: Context, userName: String, chatName: String) : Intent{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(INTENT_USER_NAME, userName)
            intent.putExtra(INTENT_CHAT_NAME, chatName)
            return intent
        }


        const val DEFAULT_MSG_LENGTH_LIMIT = 1000
        const val CHAT_ID_KEY = "CHAT_ID_KEY"
        const val CHATTING_PARTNER_KEY = "CHATTING_PARTNER_KEY"
        const val CHATTING_PARTNER_PROFILE_PICTURE_KEY =
            "CHATTING_PARTNER_PROFILE_PICTURE_KEY"
    }

    private lateinit var userName : String
    private lateinit var chatName : String
    private lateinit var messageList: List<Message>

//    private lateinit var recyclerView: RecyclerView
//    private lateinit var viewAdapter: RecyclerView.Adapter<*>
//    private lateinit var viewManager: RecyclerView.LayoutManager

    val hessianFactory: HessianProxyFactory = HessianProxyFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.hessianchatclient.R.layout.activity_chat)

        messageList = generateSampleMessageList()

        userName = intent.getStringExtra(INTENT_USER_NAME)
        chatName = intent.getStringExtra(INTENT_CHAT_NAME)

        Toast.makeText(this, "$userName : $chatName", Toast.LENGTH_SHORT).show()

        recyclerview_message_list.apply {
//            setHasFixedSize(true)
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
            val chatService =
                hessianFactory.create(ChatService::class.java, HESSIAN_CHAT_URL) as ChatService
            val chatRoom = chatService.getAllMessages(3L)
            messageList = chatRoom.messageList
            (recyclerview_message_list.adapter as ChatRecyclerViewAdapter).setItems(messageList)
            recyclerview_message_list.adapter?.notifyDataSetChanged()
        }
    }
}
