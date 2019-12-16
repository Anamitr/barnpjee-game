package api.service;

import api.model.ChatRoom;
import api.model.Message;

public interface ChatService {

    ChatRoom getAllMessages(Long chatRoomId);
//    List<Message> getLastNMessages(String chatName, Integer n);

    Long postMessage(Long chatRoomId, Message message);
}
