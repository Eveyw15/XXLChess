package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.util.*;

import static XXLChess.Background.*;
import static XXLChess.Interaction.*;
import static XXLChess.LoadImage.*;

public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;
    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE;
    public static final int FPS = 60;
    public static double piece_movement_speed;
    public static int max_movement_time;
    public boolean white_turn= true;
    public String configPath;
    public ChessPiece selected;
    public Map map;

    int lastTimeUpdate = millis(); // Store the time of the last update
    String playerTimeDisplay;
    String cpuTimeDisplay;
    int playerSeconds;
    int cpuSeconds;
    int playerIncrement;
    int cpuIncrement;
    String playerColor;
    String state = "START_MENU";

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        // Load images during setup
        LoadImage.loadImages(this);

		// load config
        JSONObject conf = loadJSONObject(new File(this.configPath));

        String layout = conf.getString("layout");
        JSONObject timeControls = conf.getJSONObject("time_controls");
        JSONObject playerTimeControls = timeControls.getJSONObject("player");
        JSONObject cpuTimeControls = timeControls.getJSONObject("cpu");

        playerSeconds = playerTimeControls.getInt("seconds");
        playerIncrement = playerTimeControls.getInt("increment");
        cpuSeconds = cpuTimeControls.getInt("seconds");
        cpuIncrement = cpuTimeControls.getInt("increment");
        playerTimeDisplay = timeDisplay(playerSeconds);
        cpuTimeDisplay = timeDisplay(cpuSeconds);

        playerColor = conf.getString("player_colour");
        piece_movement_speed = conf.getDouble("piece_movement_speed");
        max_movement_time = conf.getInt("max_movement_time");

        map = new Map(this, layout);
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if (key == 'r' || key == 'R') {
            setup();    // restart the game
        } else if (key == 'e' || key == 'E'){
            gameOver("You resigned!");  // resign
        } else if (key == '1'){
            state = "AI";   // AI mode
        } else if (key == '2') {
            state = "2-player"; // 2-player mode
        }

    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
    }

    /**
     * Receive mouse pressed signal.
     */
    @Override
    public void mousePressed(MouseEvent e) {

        if (state.equals("AI")){
            ChessPiece piece = null;
            int[] coordinate = getMouseCoordinate(e.getX(), e.getY());
            if (coordinate != null){    // if the position is not invalid, get piece at that position
                piece = getPiece(map, coordinate);
            }
            if (selected == null) {     // haven't chosen a piece before
                if (piece == null){
                    selected = null;
                } else if (piece.color == Color.WHITE) {
                    map.selectedPiece(coordinate);
                    selected = piece;
                }
            } else if (selected.color == Color.WHITE) { // white turn and choose the destination
                if (coordinate == null){
                    map.cancelSelection(map.background[selected.x][selected.y]);
                    selected = null;
                } else if (map.isPieceMoving(selected, coordinate[0], coordinate[1])) { // if piece is moving
                    playerSeconds += playerIncrement;   // add 2 seconds after moving for player
                    selected = null;

                    int[] movement = map.bestMovement();    // AI movement
                    selected = getPiece(map, new int[]{movement[1], movement[0]});
                    map.selectedPiece(new int[]{movement[1], movement[0]});
                    map.isPieceMoving(selected, movement[3], movement[2]);
                    cpuSeconds += cpuIncrement;         // add 2 seconds after moving for CPU
                    selected = null;
                } else if (map.piece[coordinate[1]][coordinate[0]] != null && map.piece[coordinate[1]][coordinate[0]] != selected && map.piece[coordinate[1]][coordinate[0]].color == selected.color) {
                    map.selectedPiece(coordinate);  // choose another white piece
                    selected = piece;
                } else {
                    selected = null;        // invalid piece
                }
            } else {
                selected = null;            // cannot choose black piece
            }


        } else if (state.equals("2-player")) {
            ChessPiece piece = null;
            int[] coordinate = getMouseCoordinate(e.getX(), e.getY());
            if (coordinate != null){
                piece = getPiece(map, coordinate);
            }
            if (white_turn) {
                if (selected == null) {
                    if (piece == null){
                        selected = null;
                    } else if (piece.color == Color.WHITE) {
                        map.selectedPiece(coordinate);
                        selected = piece;
                    }
                } else if (selected.color == Color.WHITE) {
                    if (coordinate == null){
                        map.cancelSelection(map.background[selected.x][selected.y]);
                        selected = null;
                    } else if (map.isPieceMoving(selected, coordinate[0], coordinate[1])) {
                        white_turn = false;
                        playerSeconds += playerIncrement;
                        selected = null;
                    } else if (map.piece[coordinate[1]][coordinate[0]] != null && map.piece[coordinate[1]][coordinate[0]] != selected && map.piece[coordinate[1]][coordinate[0]].color == selected.color) {
                        map.selectedPiece(coordinate);
                        selected = piece;
                    } else {
                        selected = null;
                    }
                } else {
                    selected = null;
                }
            } else {
                if (selected == null) {
                    if (piece == null){
                        selected = null;
                    } else if (piece.color == Color.BLACK) {
                        map.selectedPiece(coordinate);
                        selected = piece;
                    }
                } else if (selected.color == Color.BLACK) {
                    if (coordinate == null){
                        map.cancelSelection(map.background[selected.x][selected.y]);
                        selected = null;
                    } else if (map.isPieceMoving(selected, coordinate[0], coordinate[1])) {
                        white_turn = true;
                        selected = null;
                    } else if (map.piece[coordinate[1]][coordinate[0]] != null && map.piece[coordinate[1]][coordinate[0]].color == selected.color) {
                        map.selectedPiece(coordinate);
                        selected = piece;
                    } else {
                        selected = null;
                    }
                } else {
                    selected = null;
                }
            }
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }


    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {

        background(180, 180, 180);  // background for timer


        if (state.equals("START_MENU")) {   // start menu
            // Draw start menu
            textAlign(CENTER, CENTER);
            textSize(40);
            text("Start menu", width / 2, height * 2 / 5);
            textSize(28);
            text("Select mode", width / 2, height * 8 / 15);
            textSize(20);
            text("Press 1 for AI", width / 2, height * 10 / 15);
            text("Press 2 for 2-player", width / 2, height * 11 / 15);
        } else if (state.equals("AI") || state.equals("2-player")) {
            map.draw(); // Draw game
            int currentTime = millis(); // Update the timer

            if (currentTime - lastTimeUpdate >= 1000) { // 1000 milliseconds = 1 second
                if (white_turn) {
                    playerSeconds--;
                    playerTimeDisplay = timeDisplay(playerSeconds);
                } else {
                    cpuSeconds--;
                    cpuTimeDisplay = timeDisplay(cpuSeconds);
                }
                lastTimeUpdate = currentTime;   // update the time left
            }

            // display the timers
            fill(255);
            textSize(28);
            text(playerTimeDisplay, WIDTH - SIDEBAR / 2, HEIGHT * 3 / 4);
            text(cpuTimeDisplay, WIDTH - SIDEBAR / 2, HEIGHT / 4);


            if (playerSeconds == 0){
                gameOver("You lost on time!");
            } else if (cpuSeconds == 0){
                gameOver("You won on time!");
            }
            if (white_turn){
                if(map.isCheckmate(Color.BLACK)){
                    gameOver("You won by\ncheckmate!");
                }
            } else if (map.isCheckmate(Color.WHITE)){
                gameOver("You lost by\ncheckmate!");
            }
        }
    }

    /**
     * Game over.
     */
    public void gameOver(String winner) {
        textSize(16);
        text(winner, WIDTH - SIDEBAR / 2, HEIGHT / 2);
        noLoop();   // stop calling draw()
    }

    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

}
