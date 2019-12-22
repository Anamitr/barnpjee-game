package server;

import api.model.ChatRoom;
import api.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MinesweeperRESTController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Welcome to Minesweeper API!";
    }

}
