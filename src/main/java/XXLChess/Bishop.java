package XXLChess;

import java.awt.*;
import java.util.ArrayList;

import static XXLChess.Background.State.*;

public class Bishop extends ChessPiece{
    public Bishop(Color color, int x, int y) {
        super(Type.BISHOP, color, x, y, 3.625);
        if (color == Color.BLACK) {
            image = LoadImage.bBishop;
        } else {
            image = LoadImage.wBishop;
        }
    }

    /**
     * Get bishop's valid area according to the rule.
     */
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        // left-top
        for (int i = 1; i < 14; i++) {
            int new_x = this.x - i;
            int new_y = this.y - i;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)) {
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        // right-top
        for (int i = 1; i < 14; i++) {
            int new_x = this.x - i;
            int new_y = this.y + i;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)) {
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        // left-bottom
        for (int i = 1; i < 14; i++) {
            int new_x = this.x + i;
            int new_y = this.y - i;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)) {
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        // right-bottom
        for (int i = 1; i < 14; i++) {
            int new_x = this.x + i;
            int new_y = this.y + i;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)) {
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        return possibleMoves;
    }
}
