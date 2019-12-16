package server;

import api.model.ChatRoom;
import api.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ChatRepository {
    Logger logger = LoggerFactory.getLogger(ChatRepository.class);
    private static final String DB_FILE_NAME = "chatsRooms.ser";

    ChatRepository() {
        loadChatRooms();
    }

    Map<Long, ChatRoom> chatRooms = new HashMap<>();
    private final AtomicLong chatRoomCounter = new AtomicLong();

    public Long addMessage(Long chatRoomId, Message message) {
        ChatRoom chat = getChatRoom(chatRoomId);
        List<Message> messageList = chat.getMessageList();
        Message newMessage = new Message((long) messageList.size(), message.getUser(), message.getMessageContent());
        messageList.add(newMessage);
        chatRooms.put(chatRoomId, chat);
        saveChatRooms();
        return (long) messageList.size();
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        ChatRoom chat = chatRooms.get(chatRoomId);
        if(chat == null) {
            chat = new ChatRoom(chatRoomCounter.incrementAndGet());
        }
        return chat;
    }

    public List<Message> findMessagesNewerThan(Long chatRoomId, Long lastMessageId) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);
        List<Message> messageList = chatRoom.getMessageList();
        return messageList.subList((int)(lastMessageId+1), messageList.size());
    }

    private void saveChatRooms() {
        try {
            File file = new File(DB_FILE_NAME);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(chatRooms);
            out.close();
            fileOutputStream.close();
            logger.info("Saved database at \"chatsRooms.ser\"");

        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void loadChatRooms() {
        try {
            FileInputStream fileInputStream = new FileInputStream(DB_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            chatRooms = (Map<Long, ChatRoom>) in.readObject();
            in.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
        logger.info("Loaded chatRooms");
    }

    private static ChatRepository instance;

    public static ChatRepository getInstance() {
        if (instance == null) {
            instance = new ChatRepository();
        }
        return instance;
    }
}
