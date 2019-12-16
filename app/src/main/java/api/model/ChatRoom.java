package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Serializable {
    private Long id;
    private List<Message> messageList = new ArrayList<>();

    public ChatRoom(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

//    public void setMessageList(List<Message> messageList) {
//        this.messageList = messageList;
//    }


    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", messageList=" + messageList +
                '}';
    }
}
