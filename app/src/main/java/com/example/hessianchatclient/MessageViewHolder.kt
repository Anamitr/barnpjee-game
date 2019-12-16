package com.example.hessianchatclient

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import api.model.Message


class MessageViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
    ViewHolder(
        inflater.inflate(
            com.example.hessianchatclient.R.layout.item_message,
            parent,
            false
        )
    ) {
    val messageText: TextView

    init {
        messageText = itemView.findViewById(com.example.hessianchatclient.R.id.text_message_body)
    }

    fun bind(message: Message) {
        messageText.text = message.messageContent
    }
}
