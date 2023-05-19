package XXLChess;

import java.awt.*;
import java.util.ArrayList;

public class Chancellor extends ChessPiece{
    public Chancellor(Color color, int x, int y) {
        super(Type.CHANCELLOR, color, x, y, 8.5);
        if (color == Color.BLACK) {
            image = LoadImage.bChancellor;
        } else {
            image = LoadImage.wChancellor;
        }
    }

    /**
     * Get chancellor's valid area according to the rule. (Knight + Rook)
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Knight tempKnight = new Knight(color, x, y);
        Rook tempRook = new Rook(color, x, y);

        possibleMoves.addAll(tempRook.movingArea(map));
        possibleMoves.addAll(tempKnight.movingArea(map));
        return possibleMoves;
    }
}
