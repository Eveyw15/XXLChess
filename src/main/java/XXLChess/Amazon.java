package XXLChess;

import java.util.ArrayList;

public class Amazon extends ChessPiece{
    public Amazon(Color color, int x, int y) {
        super(Type.AMAZON, color, x, y, 12);
        if (color == Color.BLACK) {
            image = LoadImage.bAmazon;
        } else {
            image = LoadImage.wAmazon;
        }
    }

    /**
     * Get amazon's valid area according to the rule. (Knight + Bishop + Rook)
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Knight tempKnight = new Knight(color, x, y);
        Bishop tempBishop = new Bishop(color, x, y);
        Rook tempRook = new Rook(color, x, y);

        // call temp method
        possibleMoves.addAll(tempRook.movingArea(map));
        possibleMoves.addAll(tempBishop.movingArea(map));
        possibleMoves.addAll(tempKnight.movingArea(map));
        return possibleMoves;
    }
}
