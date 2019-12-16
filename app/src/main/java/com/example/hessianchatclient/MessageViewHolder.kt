package com.example.hessianchatclient

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import api.model.Message


class MessageViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
    ViewHolder(
        inflater.inflate(
            R.layout.item_message,
            parent,
            false
        )
    ) {
    private val usernameTextView : TextView = itemView.findViewById(R.id.username_textview)
    private val messageText: TextView = itemView.findViewById(R.id.message_body_textview)

    fun bind(message: Message) {
        usernameTextView.text = message.user
        messageText.text = message.messageContent
    }
}
