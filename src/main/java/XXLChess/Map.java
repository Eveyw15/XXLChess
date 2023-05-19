package XXLChess;

import java.io.*;
import java.util.*;

import com.google.common.base.MoreObjects;
import processing.core.PApplet;
import processing.core.PImage;
import static XXLChess.App.*;
import static XXLChess.Background.State.*;
import static XXLChess.Interaction.*;
import static XXLChess.Background.*;

public class Map {
    private int flashCounter = -1;
    private boolean isFlashing = false;
    private boolean isFlashOn = false;
    public ArrayList<int[]> changedPosition;
    Character[][] piece_default;
    ChessPiece[][] piece;
    Background[][] background;
    private Random random = new Random();
    public App app;

    public Map(App app, String layout) {
        this.app = app;
        piece_default = new Character[BOARD_WIDTH][BOARD_WIDTH];
        piece = new ChessPiece[BOARD_WIDTH][BOARD_WIDTH];
        background = new Background[BOARD_WIDTH][BOARD_WIDTH];
        loadingBoard();
        if(layout != null){
            loadingPiece(layout);
        }
    }

    /**
     * Draw board and pieces.
     */
    public void draw() {
        // draw chess board
        for (int i = 0; i < background.length; i++){
            for (int j = 0; j < background[i].length; j++){
                    background[i][j].draw(app);
            }
        }
        // draw chess piece
        for (int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != null) {
                    piece[i][j].draw(app, piece[i][j].getImage(), piece[i][j].getX(), piece[i][j].getY());
                    //isCheckmate(piece[i][j]);
                    if (piece[i][j].isKingInCheck){
                        app.fill(255);
                        app.textSize(32);
                        app.text("Check!", WIDTH - SIDEBAR / 2, HEIGHT / 2);

                        if (flashCounter == -1) {
                            isFlashing = true;  // start flashing
                            flashCounter = 6;   // 2 * 3 flashes
                        }
                        // Create a flashing effect
                    if (isFlashing) {
                        if (app.frameCount % 30 == 0) { // this will be true every 0.5 seconds
                            isFlashOn = !isFlashOn;
                            flashCounter--;
                            if (flashCounter == 0) {
                                isFlashing = false; // end flashing
                            }
                        }
                        if (isFlashOn) {
                            app.fill(215, 0, 0); // fill red color
                        } else {
                            if (background[j][i].color == Color.BLACK){ // set tile color to regular
                                app.fill(181, 136, 99);
                            } else {
                                app.fill(240, 217, 181);
                            }
                        }
                        app.rect(j * CELLSIZE, i * CELLSIZE, CELLSIZE, CELLSIZE);

                        app.image(this.piece[i][j].image, j * CELLSIZE, i * CELLSIZE);  // draw the King's image again
                        }
                    }
                }
            }
        }

    }

    /**
     * Get the piece that mouse clicked and its movable tiles.
     */
    public void selectedPiece(int[] coordinate){
        int x = coordinate[0];
        int y = coordinate[1];
        if (selectedX == -1 && selectedY == -1){ // select a piece for the first time
            selectedX = x;
            selectedY = y;
            changedPosition = piece[selectedY][selectedX].movingArea(this); // get tiles that need to change its color
            possibleDestination(piece[selectedY][selectedX]);   // set tiles' state
            selectedX = -1;
            selectedY = -1;

        } else if (selectedX != -1 && selectedY != -1){ // change to a new piece
            selectedX = x;
            selectedY = y;
            changedPosition = piece[selectedY][selectedX].movingArea(this);
            possibleDestination(piece[selectedY][selectedX]);
            selectedX = -1;
            selectedY = -1;
        }
    }

    /**
     * Set different color state for the possible destinations.
     */
    public void possibleDestination(ChessPiece selected){
        background[selected.y][selected.x].setState(SELECTED);
        for (int i = 0; i < changedPosition.size(); i++){
            int dx = changedPosition.get(i)[1];
            int dy = changedPosition.get(i)[0];

            if (piece[dx][dy] != null){ // having piece at there
                if (piece[dx][dy].type == ChessPiece.Type.KING) {   // if the piece is a king
                    background[dy][dx].setState(CHECK_MATE);        // set color to red
                } else {
                    background[dy][dx].setState(CAPTURED);          // set color to orange
                }
            } else {
                background[dy][dx].setState(HIGHLIGHTED);           // set color to blue (no piece)
            }
        }
    }

    /**
     * Set piece's destination and change the original and later position's color.
     */
    public boolean isPieceMoving(ChessPiece selected, int end_i, int end_j) {
        Background original = background[selected.y][selected.x];   // store the original place
        if (isMoveValid(selected, end_i, end_j)) {
            piece[selected.x][selected.y] = null;   // clear the original piece
            selected.setX(end_j);   // set piece at the destination
            selected.setY(end_i);
            if (selected.type == ChessPiece.Type.PAWN && end_j == 7) {  // if it is a pawn, turn into a queen
                piece[selected.x][selected.y] = new Queen(selected.color, selected.x, selected.y);
            } else {    // update the piece collection
                piece[selected.x][selected.y] = selected;
            }
            cancelSelection(background[original.i][original.j]);    // delete the original highlighted color
            background[original.i][original.j].setState(Background.State.MOVING);   // update the background to green
            background[end_i][end_j].setState(Background.State.MOVING);

            changedPosition.add(new int[]{original.i, original.j}); // add coordinate of 2 green tile
            changedPosition.add(new int[]{end_j, end_i});
            return true;
        } else { // invalid area
            cancelSelection(background[original.i][original.j]);    // cannot move delete its original color
            return false;
        }
    }

    /**
     * Reset the background after piece moving.
     */
    public void cancelSelection(Background startPiece) {
        if (background[startPiece.i][startPiece.j].state == SELECTED)
            background[startPiece.i][startPiece.j].setState(REGULAR);
        for (int[] position : changedPosition) {
            if (background[position[0]][position[1]].state == CHECK_MATE)
                piece[position[1]][position[0]].isKingInCheck = false;  // not in check anymore
            background[position[0]][position[1]].setState(REGULAR);
        }
        changedPosition.clear();
    }

    /**
     * Whether the piece can move.
     */
    public boolean isMoveValid(ChessPiece selected, int i, int j) {
        if (selected.x == j && selected.y == i){    // click the same piece twice
            return false;
        } else if (piece[j][i] != null && piece[j][i].color == selected.color){ // click the piece with the same color
            cancelSelection(background[selected.y][selected.x]);
            return false;
        } else if (background[i][j].state != CAPTURED && background[i][j].state != HIGHLIGHTED){    // the destination haven't been highlighted
            return false;
        }
        return true;
    }

    /**
    * AI movement.
    */
    public int[] bestMovement() {
        double maxValue = 0;
        ChessPiece nextPiece = null;
        ChessPiece originalPiece = null;
        List<ChessPiece> blackPieces = new ArrayList<>();
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != null && piece[i][j].color == Color.BLACK) {
                    blackPieces.add(piece[i][j]);   // store all black pieces
                    for (int[] position : changedPosition) {
                        background[position[0]][position[1]].setState(REGULAR); // reset background state
                    }
                    changedPosition.clear();
                    changedPosition = piece[i][j].movingArea(this); // find movable position for that black piece
                    possibleDestination(piece[i][j]);   // set background state
                    for (int a = 0; a < changedPosition.size(); a++) {
                        ChessPiece tempPiece = getPiece(this, changedPosition.get(a));
                        if (tempPiece != null && tempPiece.value > maxValue) {  // if there is a white piece in its movable position
                            maxValue = tempPiece.value; // choose the one with the highest value
                            nextPiece = tempPiece;
                            originalPiece = piece[i][j];
                        }
                    }
                    cancelSelection(background[j][i]);  // clear its highlighted background
                }
            }
        }
        if (nextPiece == null){ // if black piece can't attack white piece
            int[] num = null;
            while (num == null) {
                originalPiece = blackPieces.get(random.nextInt(blackPieces.size()));    // choose a black piece randomly
                changedPosition.clear();
                changedPosition = originalPiece.movingArea(this);
                if (changedPosition.size() != 0)
                    num = changedPosition.get(random.nextInt(changedPosition.size()));  // choose a destination randomly
            }
            return new int[] {originalPiece.x, originalPiece.y, num[1], num[0]};
        } else
            return new int[] {originalPiece.x, originalPiece.y, nextPiece.x, nextPiece.y};
    }

    /**
     * Checkmate.
     */
    public boolean isCheckmate(Color color){
        for (int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece[i].length; j++){
                // if the king is in check and has the same color with opponent
                if (piece[i][j] != null && piece[i][j].isKingInCheck && piece[i][j].color == color){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Initialization for board.
     */
    private void loadingBoard() {

        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                int x = i * CELLSIZE;
                int y = j * CELLSIZE;
                if ((i + j) % 2 == 0) {
                    background[i][j] = new Background(Color.WHITE, i, j);
                } else {
                    background[i][j] = new Background(Color.BLACK, i, j);
                }
            }
        }
    }

    /**
     * Initialization for pieces.
     */
    private void loadingPiece(String filename) {
        try {
            File f = new File(filename);
            Scanner scan = new Scanner(f);
            for (int i = 0; i < BOARD_WIDTH; i++) {
                String line = scan.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    char yAxis = line.charAt(j);
                    piece_default[i][j] = yAxis;
                    if (yAxis == 'R') {
                        piece[i][j] = new Rook(Color.BLACK, i, j);
                    } else if (yAxis == 'r') {
                        piece[i][j] = new Rook(Color.WHITE, i, j);
                    } else if (yAxis == 'N') {
                        piece[i][j] = new Knight(Color.BLACK, i, j);
                    } else if (yAxis == 'n') {
                        piece[i][j] = new Knight(Color.WHITE, i, j);
                    } else if (yAxis == 'B') {
                        piece[i][j] = new Bishop(Color.BLACK, i, j);
                    } else if (yAxis == 'b') {
                        piece[i][j] = new Bishop(Color.WHITE, i, j);
                    } else if (yAxis == 'H') {
                        piece[i][j] = new Archbishop(Color.BLACK, i, j);
                    } else if (yAxis == 'h') {
                        piece[i][j] = new Archbishop(Color.WHITE, i, j);
                    } else if (yAxis == 'C') {
                        piece[i][j] = new Camel(Color.BLACK, i, j);
                    } else if (yAxis == 'c') {
                        piece[i][j] = new Camel(Color.WHITE, i, j);
                    } else if (yAxis == 'G') {
                        piece[i][j] = new General(Color.BLACK, i, j);
                    } else if (yAxis == 'g') {
                        piece[i][j] = new General(Color.WHITE, i, j);
                    } else if (yAxis == 'A') {
                        piece[i][j] = new Amazon(Color.BLACK, i, j);
                    } else if (yAxis == 'a') {
                        piece[i][j] = new Amazon(Color.WHITE, i, j);
                    } else if (yAxis == 'K') {
                        piece[i][j] = new King(Color.BLACK, i, j);
                    } else if (yAxis == 'k') {
                        piece[i][j] = new King(Color.WHITE, i, j);
                    } else if (yAxis == 'E') {
                        piece[i][j] = new Chancellor(Color.BLACK, i, j);
                    } else if (yAxis == 'e') {
                        piece[i][j] = new Chancellor(Color.WHITE, i, j);
                    } else if (yAxis == 'P') {
                        piece[i][j] = new Pawn(Color.BLACK, i, j);
                    } else if (yAxis == 'p') {
                        piece[i][j] = new Pawn(Color.WHITE, i, j);
                    } else if (yAxis == 'Q') {
                        piece[i][j] = new Queen(Color.BLACK, i, j);
                    } else if (yAxis == 'q') {
                        piece[i][j] = new Queen(Color.WHITE, i, j);
                    }
                }
            }
            scan.close();
        } catch (IOException e) {
            System.out.printf("Error reading map %s\n", filename);
            e.printStackTrace();
        }
    }
}
