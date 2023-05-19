package XXLChess;

import java.awt.*;
import java.util.ArrayList;

public class Camel extends ChessPiece{
    public Camel(Color color, int x, int y) {
        super(Type.CAMEL, color, x, y, 2);
        if (color == Color.BLACK) {
            image = LoadImage.bCamel;
        } else {
            image = LoadImage.wCamel;
        }
    }

    /**
     * Get camel's valid area according to the rule.
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        int[][] offset = {{-3, -1}, {-3, 1}, {-1, -3}, {-1, 3}, {1, -3}, {1, 3}, {3, -1}, {3, 1}};
        for (int i = 0; i < offset.length; i++){
            int new_x = this.x + offset[i][0];
            int new_y = this.y + offset[i][1];
            if (isValidPosition(map, this.x, this.y, new_x, new_y)){
                possibleMoves.add(new int[]{new_y, new_x});
            } else {
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color) {
                    possibleMoves.add(new int[]{new_y, new_x});
                }
            }
        }
        return possibleMoves;
    }
}
