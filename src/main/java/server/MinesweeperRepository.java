package server;

import api.model.CheckFieldResponse;
import api.model.Minefield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MinesweeperRepository {
    Logger logger = LoggerFactory.getLogger(MinesweeperRepository.class);
    private static final String DB_FILE_NAME = "minefields.ser";

    Map<String, MinefieldGame> minefields = new HashMap<>();

    MinesweeperRepository() { loadMinefields(); }

    private void loadMinefields() {

    }

    int registerUser(String username, String minefieldName) {
        Minefield minefield = getMinefield(minefieldName);
        minefield.getPlayers().add(username);
        return 0;
    }

    public Minefield getMinefield(String minefieldName) {
        MinefieldGame minefieldGame = getMinefieldGame(minefieldName);
        return minefieldGame.getMinefield();
    }

    public MinefieldGame getMinefieldGame(String minefieldName) {
        MinefieldGame minefieldGame = minefields.get(minefieldName);
        if(minefieldGame == null) {
            minefieldGame = new MinefieldGame(minefieldName);
            minefields.put(minefieldName, minefieldGame);
        }
        return minefieldGame;
    }

    private static MinesweeperRepository instance;

    public static MinesweeperRepository getInstance() {
        if (instance == null) {
            instance = new MinesweeperRepository();
        }
        return instance;
    }

    public CheckFieldResponse checkFieldResponse(String minefieldName, String userName, int x, int y) {
        MinefieldGame minefieldGame = getMinefieldGame(minefieldName);
        return minefieldGame.checkFieldWithUser(userName, x, y);
    }

    public String getCurrentPlayer(String minefieldId) {
        MinefieldGame minefieldGame = getMinefieldGame(minefieldId);
        return minefieldGame.getCurrentPlayer();

    }

    public String registerForMinefield(String minefieldId, String username) {
        MinefieldGame minefieldGame = getMinefieldGame(minefieldId);
        return  minefieldGame.registerUser(username);
    }

    public int getLastMoveNumber(String minefieldId) {
        MinefieldGame minefieldGame = getMinefieldGame(minefieldId);
        return  minefieldGame.getLastMoveNumber();
    }
}
