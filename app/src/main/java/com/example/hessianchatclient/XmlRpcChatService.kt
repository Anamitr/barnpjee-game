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


class XmlRpcChatService(val xmlRpcClient : XmlRpcClient) : ChatService{
    companion object {
        val TAG = XmlRpcChatService::class.java.simpleName
    }

    override fun getAllMessages(chatRoomId: Long?): ChatRoom {
        Log.v(TAG, "clientConfig.isEnabledForExtensions = ${xmlRpcClient.clientConfig.isEnabledForExtensions})")
        xmlRpcClient.config.isEnabledForExtensions
        xmlRpcClient.clientConfig.isEnabledForExtensions
        return xmlRpcClient.execute("ChatService.getAllMessages", Collections.singletonList(chatRoomId)) as ChatRoom
    }

    override fun postMessage(chatRoomId: Long?, message: Message?): Long {
        return xmlRpcClient.execute("ChatService.postMessage", listOf(chatRoomId, message)) as Long
    }

    override fun getMessagesUpdate(chatRoomId: Long?, lastMessageId: Long?): MutableList<Message> {
        return xmlRpcClient.execute("ChatService.getMessageUpdate", listOf(chatRoomId, lastMessageId)) as MutableList<Message>
    }

    override fun getTestChatString(testStringArgument: String): String {
        try {
            return xmlRpcClient.execute("ChatService.getTestChatString", Collections.singletonList(testStringArgument)) as String
        } catch (e : XmlRpcException) {
            Log.e(TAG, e.localizedMessage)
            return "error"
        }
    }

}