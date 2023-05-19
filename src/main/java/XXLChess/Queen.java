package XXLChess;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends ChessPiece{
    public Queen(Color color, int x, int y) {
        super(Type.QUEEN, color, x, y, 9.5);
        if (color == Color.BLACK) {
            image = LoadImage.bQueen;
        } else {
            image = LoadImage.wQueen;
        }
    }

    /**
     * Get queen's valid area according to the rule. (Bishop + Rook)
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Bishop tempBishop = new Bishop(color, x, y);
        Rook tempRook = new Rook(color, x, y);

        possibleMoves.addAll(tempRook.movingArea(map));
        possibleMoves.addAll(tempBishop.movingArea(map));
        return possibleMoves;
    }
}
