package com.example.hessianchatclient

import android.util.Log
import api.model.ChatRoom
import api.model.Message
import api.service.ChatService
import org.apache.xmlrpc.XmlRpcException
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import java.net.URL
import java.util.*


class XmlRpcChatService(val xmlRpcClient: XmlRpcClient) : ChatService {
    companion object {
        val TAG = XmlRpcChatService::class.java.simpleName
    }

    override fun getAllMessages(chatRoomId: Long?): ChatRoom {
        try {
            Log.v(
                TAG,
                "clientConfig.isEnabledForExtensions = ${xmlRpcClient.clientConfig.isEnabledForExtensions})"
            )
            xmlRpcClient.config.isEnabledForExtensions
            xmlRpcClient.clientConfig.isEnabledForExtensions
            return xmlRpcClient.execute(
                "ChatService.getAllMessages",
                Collections.singletonList(chatRoomId)
            ) as ChatRoom
        } catch (e: XmlRpcException) {
            Log.e(TAG, e.localizedMessage)
            return ChatRoom(-1)
        }
    }

    override fun postMessage(chatRoomId: Long?, message: Message?): Long {
        try {
            return xmlRpcClient.execute(
                "ChatService.postMessage",
                listOf(chatRoomId, message)
            ) as Long
        } catch (e: XmlRpcException) {
            Log.e(TAG, e.localizedMessage)
            return -1L
        }
    }

    override fun getMessagesUpdate(chatRoomId: Long?, lastMessageId: Long?): List<Message> {
        try {
            val result = xmlRpcClient.execute(
                "ChatService.getMessagesUpdate",
                listOf(chatRoomId, lastMessageId)
            ) as Array<*>
            val messageList = ArrayList<Message>()

            for(message in result) {
                messageList.add(message as Message)
            }

            return messageList
        } catch (e: XmlRpcException) {
            Log.e(TAG, e.localizedMessage)
            return mutableListOf()
        }
    }

    override fun getTestChatString(testStringArgument: String): String {
        try {
            return xmlRpcClient.execute(
                "ChatService.getTestChatString",
                Collections.singletonList(testStringArgument)
            ) as String
        } catch (e: XmlRpcException) {
            Log.e(TAG, e.localizedMessage)
            return "error"
        }
    }

}