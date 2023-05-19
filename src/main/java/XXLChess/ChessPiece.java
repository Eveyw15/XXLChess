package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static XXLChess.App.*;
import static XXLChess.Background.State.*;
import static XXLChess.Interaction.*;

public abstract class ChessPiece {

    public enum Type {
        PAWN,
        QUEEN,
        AMAZON,
        ARCHBISHOP,
        BISHOP,
        CAMEL,
        CHANCELLOR,
        GENERAL,
        KING,
        KNIGHT,
        ROOK
    }

    protected int x;
    protected int y;
    protected double value;
    protected double speed = piece_movement_speed;
    protected boolean isKingInCheck;

    PImage image;
    Type type;
    Color color;

    /**
     * Get piece's valid area according to the rule.
     */
    public abstract ArrayList<int[]> movingArea(Map map);

    public ChessPiece(Type type, Color color, int x, int y, double value){
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = color;
        this.value = value;
        this.isKingInCheck = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PImage getImage() {
        return image;
    }

    /**
     * Draw images.
     */
    public void draw(App app, PImage image, int x, int y) {
        drawImages(app, image, x, y);
    }

    /**
     * Whether the position is movable.
     */
    public boolean isValidPosition(Map map, int i, int j, int new_i, int new_j) {

        if (new_i >= 0 && new_i < BOARD_WIDTH && new_j >= 0 && new_j < BOARD_WIDTH) { // the position is inside the board
            if (map.piece[i][j].type == Type.KING) {    // if it is a king

                for (int u = 0; u < map.piece.length; u++) {
                    for (int v = 0; v < map.piece[u].length; v++) {
                        // traversing the opponent's pieces
                        if (map.piece[u][v] != null && map.piece[u][v].color != map.piece[i][j].color && map.piece[u][v].type != Type.KING) {

                            ArrayList<int[]> possibleMoves = map.piece[u][v].movingArea(map);
                            for (int a = 0; a < possibleMoves.size(); a++) {
                                Background temp = map.background[possibleMoves.get(a)[0]][possibleMoves.get(a)[1]];
                                if (temp == map.background[new_j][new_i]) { // if opponent's piece can move to the king's valid area
                                    return false;   // shouldn't move
                                }
                            }
                        }
                    }
                }
                if (map.piece[new_i][new_j] != null) {  // having piece at the destination
                    return false;
                } else {    // no piece at the destination
                    return true;
                }
            }
            else if (map.piece[new_i][new_j] != null) { // having piece at the destination
                // if it's opponent's king
                if (map.piece[i][j].color != map.piece[new_i][new_j].color && map.piece[new_i][new_j].type == Type.KING) {
                    map.piece[new_i][new_j].isKingInCheck = true;
                    return true;
                } else{
                    return false;
                }
            } else {
                return true; // map.piece[new_i][new_j] == null
            }

        }
        return false;   // invalid position
    }
}