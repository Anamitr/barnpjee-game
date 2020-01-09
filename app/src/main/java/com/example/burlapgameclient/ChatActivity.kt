package com.example.burlapgameclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnLayoutChangeListener
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
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.net.URL


class ChatActivity : AppCompatActivity() {
    companion object {
        private val TAG = ChatActivity::class.java.simpleName

        private val INTENT_USER_NAME = "INTENT_USER_NAME"
        private val INTENT_CHAT_NAME = "INTENT_CHAT_NAME"
        private val INTENT_PROTOCOL = "INTENT_PROTOCOL"

        fun newIntent(
            context: Context,
            userName: String,
            chatId: String,
            protocol: Protocol
        ): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(INTENT_USER_NAME, userName)
            intent.putExtra(INTENT_CHAT_NAME, chatId)
            intent.putExtra(INTENT_PROTOCOL, protocol)
            return intent
        }
    }

    private lateinit var userName: String
    private var chatRoomId: Long = -1
    private var messageList: MutableList<Message> = ArrayList()
    private var protocol: Protocol = Protocol.HESSIAN


    private lateinit var chatService: ChatService

//    val chatService =
//        hessianFactory.create(ChatService::class.java, HESSIAN_CHAT_URL) as ChatService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageList = ArrayList()

        userName = intent.getStringExtra(INTENT_USER_NAME)
        chatRoomId = intent.getStringExtra(INTENT_CHAT_NAME).toLong()
        protocol = intent.getSerializableExtra(INTENT_PROTOCOL) as Protocol

//        Toast.makeText(this, "$userName : $chatRoomId", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Protocol: $protocol", Toast.LENGTH_SHORT).show()

        createChatService()

        recyclerview_message_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = ChatRecyclerViewAdapter(messageList)
        }
        recyclerview_message_list.addOnLayoutChangeListener(OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                recyclerview_message_list.postDelayed(Runnable {
                    Log.v(TAG, "scrollToPosition layoutChange listener")
                    scrollToBottom()
                }, 100)
            }
        })


        loadAllMessages()

        scheduleChatSync()

        return
    }

    private fun createChatService() {
        if (protocol == Protocol.HESSIAN) {
            val hessianFactory: HessianProxyFactory = HessianProxyFactory()
            chatService =
                hessianFactory.create(ChatService::class.java, HESSIAN_CHAT_URL) as ChatService
        } else {
            val config: XmlRpcClientConfigImpl = XmlRpcClientConfigImpl()
            config.serverURL = URL(XMLRPC_CHAT_URL)
            config.isEnabledForExtensions = true
            val xmlRpcClient = XmlRpcClient()
            xmlRpcClient.setConfig(config)

            chatService = XmlRpcChatService(xmlRpcClient)
        }
    }

    fun generateSampleMessageList(): List<Message> {
        val messageList = ArrayList<Message>()
        messageList.add(Message(-1, "Spielberg", "Hello"))
        messageList.add(Message(-1, "Parystokles", "Ich bin Vulgaris"))
        messageList.add(Message(-1, "Demistoteles", "Magistralis"))
        return messageList
    }

    fun loadAllMessages() {
        GlobalScope.launch {
            val chatRoom = chatService.getAllMessages(chatRoomId)
            EventBus.getDefault()
                .post(MessagesUpdateEvent(chatRoomId, newMessages = chatRoom.messageList))
//            refreshRecyclerViewWithList(chatRoom.messageList)
        }
    }

    fun sendMessage(view: View) {
        GlobalScope.launch {
            val message =
                Message(messageList.size.toLong(), userName, message_edit_text.text.toString())
            val newMessageId = chatService.postMessage(chatRoomId, message)

            messageList.add(message)
            refreshRecyclerView()
//            EventBus.getDefault().post(
//                NewMessageEvent(
//                    chatRoomId,
//                    Message(newMessageId, userName, message_edit_text.text.toString())
//                )
//            )
            runOnUiThread(Runnable {
                message_edit_text.setText("")
            })

//            hideKeyboard(view)
//            Toast.makeText(this@ChatActivity, "Message sent", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    val handler = Handler()

    private val checkForUpdatesRunnable: Runnable = object : Runnable {
        override fun run() {
            Log.d("Handlers", "Called on main thread")
            checkForUpdates()
            handler.postDelayed(this, 2000)
        }
    }

    private fun scheduleChatSync() {
        handler.post(checkForUpdatesRunnable)
    }

    private fun checkForUpdates() {
        GlobalScope.launch {
            try {
                val lastMessageId = messageList.size - 1
                if (lastMessageId >= 0) {
                    Log.v(TAG, "checkForUpdates , lastMessageId = $lastMessageId")
                    val newMessages =
                        chatService.getMessagesUpdate(chatRoomId, messageList.get(lastMessageId).id)
                    if (newMessages.isEmpty()) {
                        Log.v(TAG, "No new messages")
                    } else {
                        Log.v(TAG, "Downlaoded new messages: $newMessages")
                        messageList.addAll(newMessages)
                        refreshRecyclerView()
                    }
                } else {
                    Log.v(TAG, "Message list empty")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.localizedMessage)
            }

        }
    }

    @Subscribe
    fun onMessageEvent(event: NewMessageEvent) {
        if (event.chatRoomId == chatRoomId) {
            messageList.add(event.newMessage)
            refreshRecyclerView()
        }
    }

    @Subscribe
    fun onMessagesUpdateEvent(event: MessagesUpdateEvent) {
        if (event.chatRoomId == chatRoomId) {
            messageList.addAll(event.newMessages)
            refreshRecyclerView()
        }
    }

    fun refreshRecyclerViewWithList(newMessageList: MutableList<Message>) {
        messageList = newMessageList
        refreshRecyclerView()
    }

    fun refreshRecyclerView() {
        runOnUiThread(Runnable {
            (recyclerview_message_list.adapter as ChatRecyclerViewAdapter).setItems(messageList)
            recyclerview_message_list.adapter?.notifyDataSetChanged()
            Log.v(TAG, "scrollToPosition")
            scrollToBottom()
        })
    }

    private fun scrollToBottom() {
        if (messageList.isNotEmpty()) {
            recyclerview_message_list.smoothScrollToPosition(messageList.size - 1)
        }
    }

    fun messageEditTextOnClick(view: View) {
        refreshRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        handler.removeCallbacks(checkForUpdatesRunnable)
    }

    class NewMessageEvent(val chatRoomId: Long, val newMessage: Message)

    class MessagesUpdateEvent(val chatRoomId: Long, val newMessages: List<Message>)
}
