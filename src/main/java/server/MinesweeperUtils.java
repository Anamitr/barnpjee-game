package server;

import api.exception.MinefieldConst;
import api.model.FieldType;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperUtils {
    public static List<List<FieldType>> generateEmptyMinefield() {
        List<List<FieldType>> minefield = new ArrayList<>();
        for (int i = 0; i < MinefieldConst.MINEFIELD_HEIGHT; i++) {
            List<FieldType> row = new ArrayList<>();
            for (int j = 0; j < MinefieldConst.MINEFIELD_WIDTH; j++) {
                row.add(new FieldType());
            }
            minefield.add(row);
        }
        return minefield;
    }
}
