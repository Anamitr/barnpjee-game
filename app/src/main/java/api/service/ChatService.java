package api.service;

import api.model.ChatRoom;
import api.model.Message;

import java.util.List;

public interface ChatService {

    ChatRoom getAllMessages(Long chatRoomId);

    Long postMessage(Long chatRoomId, Message message);

    List<Message> getMessagesUpdate(Long chatRoomId, Long lastMessageId);

    String getTestChatString(String testStringArgument);
}
