package server.service;

import api.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ChatRepository;
import api.model.ChatRoom;
import api.model.Message;

import java.util.List;

public class ChatServiceImpl implements ChatService {

    Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    ChatRepository chatRepository = ChatRepository.getInstance();

    @Override
    public ChatRoom getAllMessages(Long chatRoomId) {
        return chatRepository.getChatRoom(chatRoomId);
    }

    @Override
    public Long postMessage(Long chatRoomId, Message message) {
        logger.info("postMessage: chatRoomId = " + chatRoomId + ", message = " + message.toString());
        return chatRepository.addMessage(chatRoomId, message);
    }

    @Override
    public List<Message> getMessagesUpdate(Long chatRoomId, Long lastMessageId) {
        List<Message> newMessages = chatRepository.findMessagesNewerThan(chatRoomId, lastMessageId);
        if(newMessages.isEmpty()) {
            logger.info("No new messages");
        } else {
            logger.info("Found " + newMessages.size() + " new messages: " + newMessages.toString());
        }
        return newMessages;
    }


}
