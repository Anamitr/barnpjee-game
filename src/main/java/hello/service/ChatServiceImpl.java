package hello.service;

import hello.ChatRepository;
import hello.model.ChatRoom;
import hello.model.Message;

import java.util.List;

public class ChatServiceImpl implements ChatService{

    ChatRepository chatRepository = ChatRepository.getInstance();

    @Override
    public ChatRoom getAllMessages(Long chatRoomId) {
        return chatRepository.getChatRoom(chatRoomId);
    }

    @Override
    public Long postMessage(Long chatRoomId, Message message) {
        return chatRepository.addMessage(chatRoomId, message);
    }
}
