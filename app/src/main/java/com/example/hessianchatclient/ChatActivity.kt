package com.example.hessianchatclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import api.model.Message
import api.service.ChatService
import com.caucho.hessian.client.HessianProxyFactory
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


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
    private var messageList: MutableList<Message> = ArrayList()

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
        messageList.add(Message(-1,"Spielberg", "Hello"))
        messageList.add(Message(-1,"Parystokles", "Ich bin Vulgaris"))
        messageList.add(Message(-1,"Demistoteles", "Magistralis"))
        return messageList
    }

    fun loadAllMessages() {
        GlobalScope.launch {
            val chatRoom = chatService.getAllMessages(chatId)
            refreshRecyclerViewWithList(chatRoom.messageList)
        }
    }

    fun sendMessage(view : View) {
        GlobalScope.launch {
            val message = Message(-1, userName, message_edit_text.text.toString())
            val newMessageId = chatService.postMessage(chatId, message)

//            messageList.add(message)
//            refreshRecyclerView()
            EventBus.getDefault().post(NewMessageEvent(chatId, Message(newMessageId, userName, message_edit_text.text.toString())))
            message_edit_text.setText("")
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
//            Toast.makeText(this@ChatActivity, "Message sent", Toast.LENGTH_SHORT).show()
        }
    }

    @Subscribe
    fun onMessageEvent(event: NewMessageEvent) {
        if(event.chatRoomId == chatId) {
            messageList.add(event.newMessage)
            refreshRecyclerView()
        }
    }

    fun refreshRecyclerViewWithList(newMessageList: MutableList<Message>) {
        messageList = newMessageList
        refreshRecyclerView()
    }

    fun refreshRecyclerView() {
        (recyclerview_message_list.adapter as ChatRecyclerViewAdapter).setItems(messageList)
        recyclerview_message_list.adapter?.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    class NewMessageEvent(val chatRoomId: Long, val newMessage: Message)
}
