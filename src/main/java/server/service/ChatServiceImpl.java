package server.service;

import api.service.ChatService;
import server.ChatRepository;
import api.model.ChatRoom;
import api.model.Message;

public class ChatServiceImpl implements ChatService {

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
