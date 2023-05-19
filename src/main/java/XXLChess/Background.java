package XXLChess;

import java.awt.*;
import processing.core.PApplet;
import processing.core.PImage;

import static XXLChess.App.*;
import static XXLChess.LoadImage.*;
import static processing.core.PConstants.PI;

public class Background {

    enum State{
        REGULAR,
        HIGHLIGHTED,
        SELECTED,
        CAPTURED,
        CHECK_MATE,
        MOVING
    }

    int i, j;
    Color color;
    State state;

    public Background(Color color, int i, int j){
        this.i = i;
        this.j = j;
        this.color = color;
        this.state = State.REGULAR;
    }

    /**
     * Draw tiles according to the state.
     */
    public void draw(App app) {
        app.noStroke();
        switch (state) {
            case REGULAR:
                if (color == Color.WHITE) {
                    app.fill(240, 217, 181); // even tiles are filled with brighter colour
                } else {
                    app.fill(181, 136, 99); // odd tiles are filled with darker colour
                }
                break;
            case HIGHLIGHTED:   // movable: blue
                if (color == Color.BLACK) {
                    app.fill(170,210,221);
                } else {
                    app.fill(196,224,232);
                }
                break;
            case SELECTED:      // selected: dark green
                app.fill(105,138,76);
                break;
            case CAPTURED:      // captured: orange
                app.fill(255,164,102);
                break;
            case CHECK_MATE:    // checkmate: red
                app.fill(215,0,0);
                break;
            case MOVING:        // after moving: light green
                if (color == Color.BLACK) {
                    app.fill(170,162,58);
                } else {
                    app.fill(205,210,106);
                }
                break;
        }
        app.rect(i * CELLSIZE, j * CELLSIZE, CELLSIZE, CELLSIZE); // draw chessboard
    }

    /**
     * Set tile's state.
     */
    public void setState(State state){
        this.state = state;
    }

}
