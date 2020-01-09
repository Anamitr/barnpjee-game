package api.model;

import api.exception.MinesweeperException;

import java.io.Serializable;

public class FieldType implements Serializable {
    boolean isBomb = false;
    boolean isRevealed = false;
    Integer bombsAround = 0;

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public Integer getBombsAround() {
        return bombsAround;
    }

    public void setBombsAround(Integer bombsAround) {
        if(bombsAround > -1 && bombsAround < 9) {
            this.bombsAround = bombsAround;
        } else {
            throw new IllegalArgumentException("bombsAround out of bounds: " + bombsAround + ", should be in (0,8)");
        }
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}
