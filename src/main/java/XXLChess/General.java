package XXLChess;

import java.awt.*;
import java.util.ArrayList;

public class General extends ChessPiece{
    public General(Color color, int x, int y) {
        super(Type.GENERAL, color, x, y, 5);
        if (color == Color.BLACK) {
            image = LoadImage.bGeneral;
        } else {
            image = LoadImage.wGeneral;
        }
    }

    /**
     * Get general's valid area according to the rule. (Knight + King)
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Knight tempKnight = new Knight(color, x, y);
        King tempKing = new King(color, x, y);

        possibleMoves.addAll(tempKing.movingArea(map));
        possibleMoves.addAll(tempKnight.movingArea(map));
        return possibleMoves;
    }
}
