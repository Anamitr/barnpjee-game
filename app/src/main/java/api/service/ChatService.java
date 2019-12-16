package api.service;

import java.util.List;

import api.model.ChatRoom;
import api.model.Message;

public interface ChatService {

    ChatRoom getAllMessages(Long chatRoomId);

    Long postMessage(Long chatRoomId, Message message);

    List<Message> getMessagesUpdate(Long chatRoomId, Long lastMessageId);
}

