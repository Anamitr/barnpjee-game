package server;

//import Math

import api.exception.MinefieldConst;
import api.model.CheckFieldResponse;
import api.model.FieldType;
import api.model.Minefield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinefieldGame implements Serializable {
    Logger logger = LoggerFactory.getLogger(MinefieldGame.class);

    String name;

    int gridW = MinefieldConst.MINEFIELD_WIDTH; // grid width
    int gridH = MinefieldConst.MINEFIELD_HEIGHT; // grid height
    int numOfFields = gridW * gridH;
    int numMines = 10; // number of mines on the board
    int[][] mines; // entry is 1 for having a mine and 0 for not
    boolean[][] flags; // entry is true if you have flagged that spot
    boolean[][] revealed; // entry is true if that spot is revealed
    int numOfRevealed = 0;
    int movesCounter = 0;

    boolean firstClick = true;
    boolean gameOver = false;
    boolean wasGameWon = false;
    int detonatedBombPositon = -1;

    List<String> players = new ArrayList<>();
    String currentPlayer = null;

    public MinefieldGame(String name) {
        this.name = name;
        setup();
        checkField(0, 0);
    }

    CheckFieldResponse checkField(int x, int y) {
        if (firstClick) {
            firstClick = false;
            do {
                clearMines();
                placeMines();
            } while (calcNear(x, y) != 0);
        }
        if (mines[x][y] != 0) {
            gameOver = true;
            detonatedBombPositon = y*gridW + x;
            logger.info("detonatedBombPositon = " + x + " * " + gridW + " + " + y + " = " + detonatedBombPositon);
            return CheckFieldResponse.BOMB;
        } else {
            reveal(x, y);
            logger.info("After checkField: numOfRevealed = " + numOfRevealed + ", numMines = " + numMines + ", numOfFields = " + numOfFields);
            if(numOfRevealed + numMines == numOfFields) {
                wasGameWon = true;
                gameOver = true;
                return CheckFieldResponse.GAME_WON;
            }
            return CheckFieldResponse.OK;
        }
    }

    CheckFieldResponse checkFieldWithUser(String userName, int x, int y) {
        if(gameOver) {
            if(wasGameWon) {
                return CheckFieldResponse.GAME_WON;
            } else {
                return CheckFieldResponse.GAME_IS_OVER;
            }
        }

        if(!userName.equals(currentPlayer)) {
            return CheckFieldResponse.NOT_YOUR_TURN;
        }
        nextPlayer();
        movesCounter++;
        return checkField(y, x);
    }

    private void nextPlayer() {
        int nextPlayerIndex = players.indexOf(currentPlayer) + 1;
        if (nextPlayerIndex == players.size()) {
            nextPlayerIndex = 0;
        }
        currentPlayer = players.get(nextPlayerIndex);
    }

    public String getCurrentPlayer() {
        if(currentPlayer == null) {
            return "Awaiting for players";
        } else {
            return currentPlayer;
        }
    }

    public String registerUser(String username) {
        if(!players.contains(username)) {
            players.add(username);
        }
        if(currentPlayer == null) {
            currentPlayer = username;
        }
        return "Registered user " + username;
    }

    public int getLastMoveNumber() {
        return movesCounter;
    }

    Minefield getMinefield() {

        Minefield result = new Minefield(name);
        result.setGameOver(gameOver);
        result.setDetonatedBombPosition(detonatedBombPositon);
        result.setWasGameWon(wasGameWon);
//        result.mines = mines.clone();
//        result.revealed = revealed.clone();
        List<List<FieldType>> fieldMatrix = MinesweeperUtils.generateEmptyMinefield();

        for(int i = 0; i < gridH; i++) {
            for(int j = 0; j < gridW; j++) {
                if(mines[j][i] == 1) {
                    fieldMatrix.get(i).get(j).setBomb(true);
                } else {
                    fieldMatrix.get(i).get(j).setBombsAround(calcNear(j, i));
                }
                if(revealed[j][i]) {
                    fieldMatrix.get(i).get(j).setRevealed(true);
                }
            }
        }
        result.setFieldsMatrix(fieldMatrix);
        logger.info("getMinefield: " + result.toString());
        return result;
    }

    void setMinefield(Minefield minefield) {
        name = minefield.getId();
        List<List<FieldType>> fieldMatrix = minefield.getFieldsMatrix();
        for(int i = 0; i < gridH; i++) {
            for(int j = 0; j < gridW; j++) {
                if(mines[i][j] == 1) {
                    fieldMatrix.get(i).get(j).setBomb(true);
                } else {
                    fieldMatrix.get(i).get(j).setBombsAround(calcNear(i, j));
                }

                if(fieldMatrix.get(i).get(j).isBomb()) {
                    mines[i][j] = 1;
                }
                if(fieldMatrix.get(i).get(j).isRevealed()) {
                    revealed[i][j] = true;
                }

            }
        }
    }

    boolean outBounds(int x, int y) {
        return x < 0 || y < 0 || x >= gridW || y >= gridH;
    }
    int calcNear(int x, int y) {
        int i = 0;
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                int posX = offsetX + x;
                int posY = offsetY + y;
                if(posX != -1 && posY != -1 && posX != mines.length && posY != mines[0].length) {
                    i += mines[posX][posY];
                }
            }
        }
        return i;
    }

    void setup() {
        //initialize and clear the arrays
        mines = new int[gridW][gridH];
        flags = new boolean[gridW][gridH];
        revealed = new boolean[gridW][gridH];
        for (int x = 0; x < gridW; x++) {
            for (int y = 0; y < gridH; y++) {
                mines[x][y] = 0;
                flags[x][y] = false;
                revealed[x][y] = false;
            }
        }
    }
    //Place numMines mines on the grid

    void placeMines() {
        int i = 0;
        Random random = new Random();
        while (i < numMines) { //We don't want mines to overlap, so while loop
            int x = random.nextInt(gridW);
            int y = random.nextInt(gridH);
            if (mines[x][y] == 1) continue;
            mines[x][y] = 1;
            i++;
        }
    }

    //Clear the mines

    void clearMines() {
        for (int x = 0; x < gridW; x++) {
            for (int y = 0; y < gridH; y++) {
                mines[x][y] = 0;
            }
        }
    }

    void reveal(int x, int y) {
        if (outBounds(x, y)) return;
        if (revealed[x][y]) return;
        revealed[x][y] = true;
        numOfRevealed++;
        if (calcNear(x, y) != 0) return;
        reveal(x - 1, y - 1);
        reveal(x - 1, y + 1);
        reveal(x + 1, y - 1);
        reveal(x + 1, y + 1);
        reveal(x - 1, y);
        reveal(x + 1, y);
        reveal(x, y - 1);
        reveal(x, y + 1);
    }
}
