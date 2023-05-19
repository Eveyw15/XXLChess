package XXLChess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static XXLChess.Background.State.REGULAR;
import static XXLChess.Interaction.getPiece;

public class King extends ChessPiece{
    public King(Color color, int x, int y) {
        super(Type.KING, color, x, y, 1.0 / 0.0);
        if (color == Color.BLACK) {
            image = LoadImage.bKing;
        } else {
            image = LoadImage.wKing;
        }
    }

    /**
     * Get king's valid area according to the rule.
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        int[][] offset = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int i = 0; i < offset.length; i++){
            int new_x = this.x + offset[i][0];
            int new_y = this.y + offset[i][1];
            if (!isValidPosition(map, this.x, this.y, new_x, new_y)) {
                if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y] != null && map.piece[new_x][new_y].color != color)
                    possibleMoves.add(new int[]{new_y, new_x});
            } else {
                possibleMoves.add(new int[]{new_y, new_x});
            }
        }
        return possibleMoves;
    }
}
