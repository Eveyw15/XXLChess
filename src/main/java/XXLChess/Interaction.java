package XXLChess;

import processing.core.PImage;

import static XXLChess.App.*;
import static XXLChess.Background.State.*;

public class Interaction {
    public static int selectedX = -1;
    public static int selectedY = -1;

    /**
     * Get the coordinate that mouse clicked.
     */
    public static int[] getMouseCoordinate(int x, int y) {
        int[] coordinate = new int[2];
        coordinate[0] = x / CELLSIZE;
        coordinate[1] = y / CELLSIZE;
        // if it is an invalid position
        if (coordinate[0] > 13 || coordinate[0] < 0 || coordinate[1] > 13 || coordinate[1] < 0)
            coordinate = null;
        return coordinate;
    }

    /**
     * Draw images.
     */
    public static void drawImages(App app, PImage image, int x, int y) {
        x = x * CELLSIZE;
        y = y * CELLSIZE;
        app.image(image, y, x);
    }

    /**
     * Get pieces by coordinate.
     */
    public static ChessPiece getPiece(Map map, int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];
        return map.piece[y][x];
    }

    /**
     * Timer.
     */
    public static String timeDisplay(int totalSeconds){
        int minutes = totalSeconds / 60; // turn seconds to minutes
        int seconds = totalSeconds % 60; // get seconds left

        return String.format("%02d:%02d", minutes, seconds);
    }
}
