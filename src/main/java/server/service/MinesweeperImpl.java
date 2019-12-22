package server.service;

import api.model.CheckFieldResponse;
import api.model.Minefield;
import api.service.MinesweeperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ChatRepository;
import api.model.ChatRoom;
import api.model.Message;

import java.util.List;

public class MinesweeperImpl implements MinesweeperService {

    Logger logger = LoggerFactory.getLogger(MinesweeperImpl.class);

    ChatRepository chatRepository = ChatRepository.getInstance();



    @Override
    public String hello() {
        return "Hello from Burlap Minesweeper server!";
    }

    @Override
    public int enterMineField(String username, String minefieldName) {
        return 0;
    }

    @Override
    public CheckFieldResponse checkField(int x, int y) {
        return null;
    }

    @Override
    public Minefield getMinefield(String minefieldId) {
        return null;
    }


}
