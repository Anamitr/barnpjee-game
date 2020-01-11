package server.service;

import api.model.CheckFieldResponse;
import api.model.Minefield;
import api.service.MinesweeperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ChatRepository;
import api.model.ChatRoom;
import api.model.Message;
import server.MinesweeperRepository;

import javax.validation.constraints.Min;
import java.util.List;

public class MinesweeperImpl implements MinesweeperService {

    Logger logger = LoggerFactory.getLogger(MinesweeperImpl.class);

    MinesweeperRepository minesweeperRepository = MinesweeperRepository.getInstance();


    @Override
    public String hello() {
        return "Hello from Burlap Minesweeper server!";
    }

    @Override
    public String getTestChatString(String argument) {
        String response = "getTestChatString was call with argument: " + argument;
        logger.info("getTestChatString return: " + response);
        return response;
    }

    @Override
    public Minefield getTestMinefield() {
        return new Minefield("test_minefield");
    }

    @Override
    public int enterMineField(String username, String minefieldName) {
        return 0;
    }

    @Override
    public CheckFieldResponse checkField(String minefieldId, String username, int x, int y) {
        CheckFieldResponse checkFieldResponse = minesweeperRepository.checkFieldResponse(minefieldId, username, x, y);
        logger.info("checkField(" + x + "," + y + ") = " + checkFieldResponse.name());
        return checkFieldResponse;
    }

    @Override
    public Minefield getMinefield(String minefieldId) {
        Minefield minefield = minesweeperRepository.getMinefield(minefieldId);
        logger.info("getMinefield(" + minefieldId + ")");
//        return minefield;
//        return new Minefield("horytnica");
        return minefield;
    }

    @Override
    public String getCurrentPlayer(String minefieldId) {
        String userName = minesweeperRepository.getCurrentPlayer(minefieldId);

        return userName;
    }


}
