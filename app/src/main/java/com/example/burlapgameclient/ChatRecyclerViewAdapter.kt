package com.example.burlapgameclient

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.model.Message

class ChatRecyclerViewAdapter(private var dataset: List<Message>) :
    RecyclerView.Adapter<MessageViewHolder>() {
    companion object {
        val TAG = ChatRecyclerViewAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message: Message = dataset[position]
        Log.v(TAG, "Binding message: ${message.messageContent}")
        holder.bind(message)
    }

    fun setItems(messageList: List<Message>) {
        this.dataset = messageList
    }

    override fun getItemCount(): Int = dataset.size
}