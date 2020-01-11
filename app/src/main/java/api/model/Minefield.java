package api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Minefield implements Serializable {
    String id;
    List<List<FieldType>> fieldsMatrix;
    List<String> players;
    boolean gameOver = false;
    int detonatedBombPosition = -1;

    public Minefield() {
        fieldsMatrix = new ArrayList<>();
        players = new ArrayList<>();
    }

    public Minefield(String id) {
        this.id = id;
        fieldsMatrix = new ArrayList<>();
        players = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<FieldType>> getFieldsMatrix() {
        return fieldsMatrix;
    }

    public void setFieldsMatrix(List<List<FieldType>> fieldsMatrix) {
        this.fieldsMatrix = fieldsMatrix;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getDetonatedBombPosition() {
        return detonatedBombPosition;
    }

    public void setDetonatedBombPosition(int detonatedBombPosition) {
        this.detonatedBombPosition = detonatedBombPosition;
    }

    @Override
    public String toString() {
        return "Minefield{" +
                "id='" + id + '\'' +
                ", fieldsMatrix=" + fieldsMatrix +
                ", players=" + players +
                '}';
    }
}
