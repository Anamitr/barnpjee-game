package api.service;

import api.model.ChatRoom;
import api.model.CheckFieldResponse;
import api.model.Message;
import api.model.Minefield;

import java.util.List;

public interface MinesweeperService {

    String hello();

    String getTestChatString(String argument);

    int enterMineField(String username, String minefieldName);

    CheckFieldResponse checkField(int x, int y);

    Minefield getMinefield(String minefieldId);
}
