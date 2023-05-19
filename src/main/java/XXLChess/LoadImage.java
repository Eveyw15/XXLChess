package XXLChess;
import processing.core.PImage;

import static XXLChess.App.CELLSIZE;

public class LoadImage {

    public static PImage bPawn;
    public static PImage wPawn;
    public static PImage bRook;
    public static PImage wRook;
    public static PImage bKnight;
    public static PImage wKnight;
    public static PImage bBishop;
    public static PImage wBishop;
    public static PImage bArchbishop;
    public static PImage wArchbishop;
    public static PImage bCamel;
    public static PImage wCamel;
    public static PImage bGeneral;
    public static PImage wGeneral;
    public static PImage bAmazon;
    public static PImage wAmazon;
    public static PImage bKing;
    public static PImage wKing;
    public static PImage bChancellor;
    public static PImage wChancellor;
    public static PImage bQueen;
    public static PImage wQueen;

    public static void loadImages(App app){
        LoadImage.bPawn = app.loadImage("src/main/resources/XXLChess/b-pawn.png");
        bPawn.resize(CELLSIZE, CELLSIZE);
        LoadImage.wPawn = app.loadImage("src/main/resources/XXLChess/w-pawn.png");
        wPawn.resize(CELLSIZE, CELLSIZE);
        LoadImage.bRook = app.loadImage("src/main/resources/XXLChess/b-rook.png");
        bRook.resize(CELLSIZE, CELLSIZE);
        LoadImage.wRook = app.loadImage("src/main/resources/XXLChess/w-rook.png");
        wRook.resize(CELLSIZE, CELLSIZE);
        LoadImage.bKnight = app.loadImage("src/main/resources/XXLChess/b-knight.png");
        bKnight.resize(CELLSIZE, CELLSIZE);
        LoadImage.wKnight = app.loadImage("src/main/resources/XXLChess/w-knight.png");
        wKnight.resize(CELLSIZE, CELLSIZE);
        LoadImage.bBishop = app.loadImage("src/main/resources/XXLChess/b-bishop.png");
        bBishop.resize(CELLSIZE, CELLSIZE);
        LoadImage.wBishop = app.loadImage("src/main/resources/XXLChess/w-bishop.png");
        wBishop.resize(CELLSIZE, CELLSIZE);
        LoadImage.bArchbishop = app.loadImage("src/main/resources/XXLChess/b-archbishop.png");
        bArchbishop.resize(CELLSIZE, CELLSIZE);
        LoadImage.wArchbishop = app.loadImage("src/main/resources/XXLChess/w-archbishop.png");
        wArchbishop.resize(CELLSIZE, CELLSIZE);
        LoadImage.bCamel = app.loadImage("src/main/resources/XXLChess/b-camel.png");
        bCamel.resize(CELLSIZE, CELLSIZE);
        LoadImage.wCamel = app.loadImage("src/main/resources/XXLChess/w-camel.png");
        wCamel.resize(CELLSIZE, CELLSIZE);
        LoadImage.bGeneral = app.loadImage("src/main/resources/XXLChess/b-knight-king.png");
        bGeneral.resize(CELLSIZE, CELLSIZE);
        LoadImage.wGeneral = app.loadImage("src/main/resources/XXLChess/w-knight-king.png");
        wGeneral.resize(CELLSIZE, CELLSIZE);
        LoadImage.bAmazon = app.loadImage("src/main/resources/XXLChess/b-amazon.png");
        bAmazon.resize(CELLSIZE, CELLSIZE);
        LoadImage.wAmazon = app.loadImage("src/main/resources/XXLChess/w-amazon.png");
        wAmazon.resize(CELLSIZE, CELLSIZE);
        LoadImage.bKing = app.loadImage("src/main/resources/XXLChess/b-king.png");
        bKing.resize(CELLSIZE, CELLSIZE);
        LoadImage.wKing = app.loadImage("src/main/resources/XXLChess/w-king.png");
        wKing.resize(CELLSIZE, CELLSIZE);
        LoadImage.bChancellor = app.loadImage("src/main/resources/XXLChess/b-chancellor.png");
        bChancellor.resize(CELLSIZE, CELLSIZE);
        LoadImage.wChancellor = app.loadImage("src/main/resources/XXLChess/w-chancellor.png");
        wChancellor.resize(CELLSIZE, CELLSIZE);
        LoadImage.bQueen = app.loadImage("src/main/resources/XXLChess/b-queen.png");
        bQueen.resize(CELLSIZE, CELLSIZE);
        LoadImage.wQueen = app.loadImage("src/main/resources/XXLChess/w-queen.png");
        wQueen.resize(CELLSIZE, CELLSIZE);
    }


}
