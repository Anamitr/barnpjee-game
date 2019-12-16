package hello.service;

import hello.model.ChatRoom;
import hello.model.Message;

import java.util.List;

public interface ChatService {

    ChatRoom getAllMessages(Long chatRoomId);
//    List<Message> getLastNMessages(String chatName, Integer n);

    Long postMessage(Long chatRoomId, Message message);
}
