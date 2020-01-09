package api.model;

import java.io.Serializable;

public class Message implements Serializable {

    private final Long id;
    private final String user;
    private final String messageContent;

    public Message(Long id, String user, String messageContent) {
        this.id = id;
        this.user = user;
        this.messageContent = messageContent;
    }

    public Long getId() {
        return id;
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
                "id=" + id +
                ", user='" + user + '\'' +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}
