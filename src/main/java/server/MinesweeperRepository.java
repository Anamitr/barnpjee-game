package server;

import api.model.Minefield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MinesweeperRepository {
    Logger logger = LoggerFactory.getLogger(MinesweeperRepository.class);
    private static final String DB_FILE_NAME = "minefields.ser";

    Map<String, Minefield> minefields = new HashMap<>();

    MinesweeperRepository() { loadMinefields(); }

    private void loadMinefields() {

    }

    int registerUser(String username, String minefieldName) {
        Minefield minefield = getMinefield(minefieldName);
        minefield.getPlayers().add(username);
        return 0;
    }

    public Minefield getMinefield(String minefieldName) {
//        Minefield minefield = minefields.get(minefieldName);
//        if(minefield == null) {
//            minefield = new Minefield(minefieldName);
//        }
        MinefieldGame minefieldGame = new MinefieldGame(minefieldName);

        return minefieldGame.getMinefield();
    }

    private static MinesweeperRepository instance;

    public static MinesweeperRepository getInstance() {
        if (instance == null) {
            instance = new MinesweeperRepository();
        }
        return instance;
    }
}
