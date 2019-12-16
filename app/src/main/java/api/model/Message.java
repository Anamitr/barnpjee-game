package api.model;

import java.io.Serializable;

public class Message implements Serializable {

    private final String user;
    private final String messageContent;

    public Message(String user, String messageContent) {
        this.user = user;
        this.messageContent = messageContent;
    }

    public String getUser() {
        return user;
    }

    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user='" + user + '\'' +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}