package com.example.hessianchatclient

import android.R
import android.R.id
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import api.model.Message
import api.model.User


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

    private val messageList: List<Message> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.hessianchatclient.R.layout.activity_chat)

        userName = intent.getStringExtra(INTENT_USER_NAME)
        chatName = intent.getStringExtra(INTENT_CHAT_NAME)

        Toast.makeText(this, "$userName : $chatName", Toast.LENGTH_SHORT).show()
    }
}
