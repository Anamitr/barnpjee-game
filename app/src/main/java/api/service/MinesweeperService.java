package api.service;

import api.model.CheckFieldResponse;
import api.model.Minefield;

public interface MinesweeperService {

    String hello();

    String getTestChatString(String argument);

    int enterMineField(String username, String minefieldName);

    CheckFieldResponse checkField(int x, int y);

    Minefield getMinefield(String minefieldId);
}
