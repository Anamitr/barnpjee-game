package hello.model;

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
}
