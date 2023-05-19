package XXLChess;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends ChessPiece{
    public Rook(Color color, int x, int y) {
        super(Type.ROOK, color, x, y, 5.25);
        if (color == Color.BLACK) {
            image = LoadImage.bRook;
        } else {
            image = LoadImage.wRook;
        }
    }

    /**
     * Get rook's valid area according to the rule.
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        // forward
        for (int i = 1; i < 14; i++){
            int new_x = this.x - i;
            int new_y = this.y;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)){
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        // backward
        for (int i = 1; i < 14; i++){
            int new_x = this.x + i;
            int new_y = this.y;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)){
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        // left
        for (int i = 1; i < 14; i++){
            int new_x = this.x;
            int new_y = this.y - i;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)){
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
                break;
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        // right
        for (int i = 1; i < 14; i++){
            int new_x = this.x;
            int new_y = this.y + i;
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)){
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
