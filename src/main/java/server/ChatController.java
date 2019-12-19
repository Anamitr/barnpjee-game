package server;

import api.model.ChatRoom;
import api.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    ChatRepository chatRepository = ChatRepository.getInstance();

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Welcome to Xml-rpc/Hessian Chat!";
    }

    @PostMapping("/message/{chatRoomId}")
    HttpStatus newMessage(@RequestBody Message message, @PathVariable Long chatRoomId) {
        chatRepository.addMessage(chatRoomId, message);
        return HttpStatus.CREATED;
    }

    @GetMapping("/messages/{chatRoomId}")
    ChatRoom getMessagesFromChatRoom(@PathVariable Long chatRoomId) {
        return chatRepository.getChatRoom(chatRoomId);
    }
}
