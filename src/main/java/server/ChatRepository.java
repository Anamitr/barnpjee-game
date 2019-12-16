package server;

import api.model.ChatRoom;
import api.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ChatRepository {
    Logger logger = LoggerFactory.getLogger(ChatRepository.class);
    private static final String DB_FILE_NAME = "chatsRooms.ser";

    ChatRepository() {
        loadChatRooms();
    }

    Map<Long, ChatRoom> chatRooms = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public Long addMessage(Long chatRoomId, Message message) {
        ChatRoom chat = getChatRoom(chatRoomId);
        chat.getMessageList().add(message);
        chatRooms.put(chatRoomId, chat);
        saveChatRooms();
        return (long) chat.getMessageList().size();
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        ChatRoom chat = chatRooms.get(chatRoomId);
        if(chat == null) {
            chat = new ChatRoom(counter.incrementAndGet());
        }
        return chat;
    }

    private void saveChatRooms() {
//        Properties properties = new Properties();
//        for (Map.Entry<Long, ChatRoom> entry : chatRooms.entrySet()) {
//            properties.put(entry.getKey().toString(), entry.getValue());
//        }
//        try {
//            properties.store(new FileOutputStream("data.properties"), null);
//        } catch (IOException e) {
//            logger.error(e.getLocalizedMessage());
//            e.printStackTrace();
//        }
        try {
            File file = new File(DB_FILE_NAME);
//            file.mkdirs();
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
//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream("data.properties"));
//        } catch (IOException e) {
//            logger.error(e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//        for (String key : properties.stringPropertyNames()) {
//            chatRooms.put(Long.getLong(key), (ChatRoom) properties.get(key));
//        }
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
