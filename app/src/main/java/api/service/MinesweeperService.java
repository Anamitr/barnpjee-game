package api.service;

import api.model.CheckFieldResponse;
import api.model.Minefield;

public interface MinesweeperService {

    String hello();

    String getTestChatString(String argument);

    Minefield getTestMinefield();

    int enterMineField(String username, String minefieldName);

    CheckFieldResponse checkField(String minefieldId, String username, int x, int y);

    Minefield getMinefield (String minefieldId);

    String getCurrentPlayer(String minefieldId);
}
