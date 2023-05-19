package XXLChess;

import java.awt.*;
import java.util.ArrayList;

public class Archbishop extends ChessPiece{
    public Archbishop(Color color, int x, int y) {
        super(Type.ARCHBISHOP, color, x, y, 7.5);
        if (color == Color.BLACK) {
            image = LoadImage.bArchbishop;
        } else {
            image = LoadImage.wArchbishop;
        }
    }

    /**
     * Get archbishop's valid area according to the rule. (Bishop + Knight)
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Knight tempKnight = new Knight(color, x, y);
        Bishop tempBishop = new Bishop(color, x, y);

        possibleMoves.addAll(tempBishop.movingArea(map));
        possibleMoves.addAll(tempKnight.movingArea(map));
        return possibleMoves;
    }
}
