package XXLChess;

import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;

import static XXLChess.App.*;
import static XXLChess.Background.State.*;

public class Pawn extends ChessPiece{

    private boolean hasMoved = false;
    public Pawn(Color color, int x, int y) {
        super(Type.PAWN, color, x, y, 1);
        if (color == Color.BLACK) {
            image = LoadImage.bPawn;    // load black pawn image
        } else {
            image = LoadImage.wPawn;    // load white pawn image
        }
    }

    /**
     * Get pawn's valid area according to the rule.
     */
    @Override
    public ArrayList<int[]> movingArea(Map map) {
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        if (color == Color.WHITE) { // white pawn
            if (x != 12){   // if not at line 13, pawn is already moved
                hasMoved = true;
            }
            if (x == 12 && !hasMoved) { // haven't moved before
                int[][] offset = {{1, 0}, {2, 0}};  // can move forward 2 space
                for (int i = 0; i < 2; i++) {
                    int new_x = this.x - offset[i][0];
                    int new_y = this.y + offset[i][1];
                    if (!isValidPosition(map, this.x, this.y, new_x, new_y)){
                        // If blocked by opponent piece, add it
                        if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                            possibleMoves.add(new int[]{new_y, new_x});
                        return possibleMoves;
                    } else{
                        possibleMoves.add(new int[]{new_y, new_x});
                    }
                }
            } else {    // moved before
                int new_x = this.x - 1; // can move forward 1 space
                int new_y = this.y;
                if (isValidPosition(map, this.x, this.y, new_x, new_y)){
                    possibleMoves.add(new int[]{new_y, new_x});
                } else {
                    if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color) {
                        possibleMoves.add(new int[]{new_y, new_x});
                    }
                }
                if (x - 1 < BOARD_WIDTH && y + 1 < BOARD_WIDTH) {   // check diagonal
                    if (map.piece[x - 1][y + 1] != null && map.piece[x - 1][y + 1].color != map.piece[x][y].color) {
                        int[] position = new int[]{y + 1, x - 1};
                        possibleMoves.add(position);
                    }
                    if (map.piece[x - 1][y - 1] != null && map.piece[x - 1][y - 1].color != map.piece[x][y].color) {
                        int[] position = new int[]{y - 1, x - 1};
                        possibleMoves.add(position);
                    }
                }
            }
        } else if (color == Color.BLACK) {  // black pawn
            if (x != 1){    // if not at line 2, pawn is already moved
                hasMoved = true;
            }
            if (x == 1 && !hasMoved) {
                int[][] offset = {{-1, 0}, {-2, 0}};
                for (int i = 0; i < 2; i++) {
                    int new_x = this.x - offset[i][0];
                    int new_y = this.y + offset[i][1];
                    if (!isValidPosition(map, this.x, this.y, new_x, new_y)){
                        if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color)
                            possibleMoves.add(new int[]{new_y, new_x});
                        return possibleMoves;
                    } else{
                        possibleMoves.add(new int[]{new_y, new_x});
                    }
                }
            } else {
                int new_x = this.x + 1;
                int new_y = this.y;
                if (isValidPosition(map, this.x, this.y, new_x, new_y)){
                    possibleMoves.add(new int[]{new_y, new_x});
                } else {
                    if (new_x >= 0 && new_y >= 0 && new_x <= 13 && new_y <= 13 && map.piece[new_x][new_y].color != color) {
                        possibleMoves.add(new int[]{new_y, new_x});
                    }
                }
                if (x - 1 < BOARD_WIDTH && y + 1 < BOARD_WIDTH) {
                    if (map.piece[x + 1][y + 1] != null && map.piece[x + 1][y + 1].color != map.piece[x][y].color) {
                        int[] position = new int[]{y + 1, x + 1};
                        possibleMoves.add(position);
                    }
                    if (map.piece[x + 1][y - 1] != null && map.piece[x + 1][y - 1].color != map.piece[x][y].color) {
                        int[] position = new int[]{y - 1, x + 1};
                        possibleMoves.add(position);
                    }
                }
            }
        }
        return possibleMoves;
    }
}
