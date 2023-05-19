package XXLChess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyTest {

    /**
     * ChessPiece
     **/
    @Test
    public void testPawn() {
        Map map = new Map(new App(), null); // new Map
        Pawn pawn = new Pawn(Color.WHITE, 12, 0);   // new pawn
        map.piece[12][0] = pawn;    // put pawn at map.piece
        map.changedPosition = map.piece[12][0].movingArea(map); // get its valid area according to the rule
        map.possibleDestination(map.piece[12][0]);  // set different color state for the possible destinations
        assertTrue(map.background[0][12].state == Background.State.SELECTED);   // map.background[0][12] is where the piece at
        assertTrue(map.background[0][11].state == Background.State.HIGHLIGHTED);    // can move forward 1 space
        assertTrue(map.background[0][10].state == Background.State.HIGHLIGHTED);    // can move forward 2 space (haven't moved before)
        assertTrue(map.isPieceMoving(pawn, 0, 10)); // can move to row11, col1
        map.cancelSelection(map.background[0][12]); // reset the color of tiles
        pawn.setX(10);
        pawn.setY(3);
        map.piece[10][3] = pawn;    // move pawn to map.piece[10][3]
        map.changedPosition = map.piece[10][3].movingArea(map);
        map.possibleDestination(map.piece[10][3]);
        assertTrue(map.background[3][9].state == Background.State.HIGHLIGHTED);
        assertFalse(map.background[3][8].state == Background.State.HIGHLIGHTED);    // cannot move forward 2 space
        assertFalse(map.isPieceMoving(pawn, 3, 8));
        map.cancelSelection(map.background[3][10]);

        pawn = new Pawn(Color.BLACK, 1, 7);
        Rook rook = new Rook(Color.WHITE, 2, 7);    // has been blocked
        Knight knight= new Knight(Color.WHITE, 2, 6);   // has opponent piece at diagonal
        map.piece[1][7] = pawn;
        map.piece[2][7] = rook;
        map.piece[3][6] = knight;
        map.changedPosition = map.piece[1][7].movingArea(map);
        map.possibleDestination(map.piece[1][7]);
        assertTrue(map.background[7][1].state == Background.State.SELECTED);
        assertTrue(map.background[7][2].state == Background.State.CAPTURED);
        assertFalse(map.background[7][3].state == Background.State.HIGHLIGHTED);    // has been blocked
        assertTrue(map.isPieceMoving(pawn, 7, 2));
        map.cancelSelection(map.background[7][1]);
        map.piece[2][7] = pawn;
        map.changedPosition = map.piece[2][7].movingArea(map);
        map.possibleDestination(map.piece[2][7]);
        assertTrue(map.background[6][3].state == Background.State.CAPTURED);    // can capture diagonal piece
        map.cancelSelection(map.background[7][2]);
    }

    @Test
    public void testAmazon() {
        Map map = new Map(new App(), null);
        Amazon amazon = new Amazon(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 2, 2);
        Rook rook = new Rook(Color.BLACK, 8, 12);
        map.piece[8][8] = amazon;
        map.piece[2][2] = pawn;
        map.piece[8][12] = rook;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[2][2].state == Background.State.HIGHLIGHTED);    // test being blocked
        assertFalse(map.background[1][1].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[12][8].state == Background.State.CAPTURED);       // test right
        assertFalse(map.background[13][8].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[9][10].state == Background.State.HIGHLIGHTED);    // test right-bottom
        assertTrue(map.background[8][5].state == Background.State.HIGHLIGHTED);     // test forward
        assertTrue(map.background[8][13].state == Background.State.HIGHLIGHTED);    // test backwards
        assertTrue(map.background[6][9].state == Background.State.HIGHLIGHTED);     // test left-bottom
        assertFalse(map.isPieceMoving(amazon, 1, 1));                            // test left-top
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testArchbishop() {
        Map map = new Map(new App(), null);
        Archbishop archbishop = new Archbishop(Color.BLACK, 0, 3);
        Pawn pawn = new Pawn(Color.BLACK, 2, 2);
        Rook rook = new Rook(Color.WHITE, 3, 6);
        map.piece[0][3] = archbishop;
        map.piece[2][2] = pawn;
        map.piece[3][6] = rook;
        map.changedPosition = map.piece[0][3].movingArea(map);
        map.possibleDestination(map.piece[0][3]);
        assertTrue(map.background[3][0].state == Background.State.SELECTED);
        assertTrue(map.background[4][2].state == Background.State.HIGHLIGHTED); // test right-bottom
        assertFalse(map.background[2][2].state == Background.State.HIGHLIGHTED);// test being blocked
        assertFalse(map.background[3][1].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[6][3].state == Background.State.CAPTURED);    // capturing piece
        assertFalse(map.background[8][4].state == Background.State.HIGHLIGHTED);
        assertFalse(map.isPieceMoving(archbishop, 2, 2));
        map.cancelSelection(map.background[3][0]);
    }

    @Test
    public void testBishop() {
        Map map = new Map(new App(), null);
        Bishop bishop = new Bishop(Color.WHITE, 5, 5);
        Pawn pawn = new Pawn(Color.WHITE, 1, 1);
        Rook rook = new Rook(Color.BLACK, 2, 8);
        map.piece[5][5] = bishop;
        map.piece[1][1] = pawn;
        map.piece[2][8] = rook;
        map.changedPosition = map.piece[5][5].movingArea(map);
        map.possibleDestination(map.piece[5][5]);
        assertTrue(map.background[5][5].state == Background.State.SELECTED);
        assertTrue(map.background[4][6].state == Background.State.HIGHLIGHTED); // test right-top
        assertFalse(map.background[1][1].state == Background.State.HIGHLIGHTED);// test being blocked
        assertTrue(map.background[8][2].state == Background.State.CAPTURED);    // test capturing
        assertFalse(map.background[9][1].state == Background.State.HIGHLIGHTED);
        assertFalse(map.isPieceMoving(bishop, 1, 1));
        map.cancelSelection(map.background[5][5]);
    }

    @Test
    public void testCamel() {
        Map map = new Map(new App(), null);
        Camel camel = new Camel(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 5, 7);
        Rook rook = new Rook(Color.BLACK, 11, 9);
        map.piece[8][8] = camel;
        map.piece[5][7] = pawn;
        map.piece[11][9] = rook;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertTrue(map.background[11][9].state == Background.State.HIGHLIGHTED);    // test row 10, col 12
        assertFalse(map.background[7][5].state == Background.State.HIGHLIGHTED);    // test being blocked
        assertTrue(map.background[9][11].state == Background.State.CAPTURED);       // test capturing
        assertTrue(map.isPieceMoving(camel, 11, 9));
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testChancellor() {
        Map map = new Map(new App(), null);
        Chancellor chancellor = new Chancellor(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 2, 8);
        Rook rook = new Rook(Color.BLACK, 7, 6);
        map.piece[8][8] = chancellor;
        map.piece[2][8] = pawn;
        map.piece[7][6] = rook;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[8][2].state == Background.State.HIGHLIGHTED);    // test being blocked
        assertFalse(map.background[8][1].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[6][7].state == Background.State.CAPTURED);        // test capturing
        assertTrue(map.background[8][10].state == Background.State.HIGHLIGHTED);    // test backwards
        assertTrue(map.background[5][8].state == Background.State.HIGHLIGHTED);     // test left
        assertTrue(map.background[13][8].state == Background.State.HIGHLIGHTED);    // test right
        assertTrue(map.background[9][10].state == Background.State.HIGHLIGHTED);    // test right-bottom
        assertTrue(map.isPieceMoving(chancellor, 6, 7));
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testGeneral() {
        Map map = new Map(new App(), null);
        General general = new General(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 9, 8);
        Rook rook = new Rook(Color.BLACK, 6, 7);
        map.piece[8][8] = general;
        map.piece[9][8] = pawn;
        map.piece[6][7] = rook;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[8][9].state == Background.State.HIGHLIGHTED);    // test being blocked
        assertTrue(map.background[7][6].state == Background.State.CAPTURED);        // test capturing
        assertTrue(map.background[9][8].state == Background.State.HIGHLIGHTED);     // test right
        assertTrue(map.background[8][7].state == Background.State.HIGHLIGHTED);     // test forward
        assertTrue(map.isPieceMoving(general, 9, 6));
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testKing() {
        Map map = new Map(new App(), null);
        King king = new King(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 9, 9);
        General general = new General(Color.BLACK, 6, 11);
        Knight knight = new Knight(Color.BLACK, 5 ,8);
        map.piece[8][8] = king;
        map.piece[9][9] = pawn;
        map.piece[6][11] = general;
        map.piece[5][8] = knight;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[7][7].state == Background.State.HIGHLIGHTED);    // cannot move to the position that can be captured
        assertFalse(map.background[9][7].state == Background.State.HIGHLIGHTED);
        assertFalse(map.background[9][9].state == Background.State.HIGHLIGHTED);    // test blocked
        assertTrue(map.background[7][9].state == Background.State.HIGHLIGHTED);     // test left-bottom
        assertFalse(map.isPieceMoving(king, 7, 7));
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testKnight() {
        Map map = new Map(new App(), null);
        Knight knight = new Knight(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 9, 10);
        Rook rook = new Rook(Color.BLACK, 6, 7);
        map.piece[8][8] = knight;
        map.piece[9][10] = pawn;
        map.piece[6][7] = rook;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[10][9].state == Background.State.HIGHLIGHTED);   // test being blocked
        assertTrue(map.background[7][6].state == Background.State.CAPTURED);        // test capturing
        assertTrue(map.isPieceMoving(knight, 7, 10));
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testQueen() {
        Map map = new Map(new App(), null);
        Queen queen = new Queen(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 11, 11);
        Camel camel = new Camel(Color.BLACK, 8, 12);
        map.piece[8][8] = queen;
        map.piece[11][11] = pawn;
        map.piece[8][12] = camel;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[11][11].state == Background.State.HIGHLIGHTED);  // test being blocked
        assertFalse(map.background[12][12].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[12][8].state == Background.State.CAPTURED);       // test capturing
        assertFalse(map.background[13][8].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[3][3].state == Background.State.HIGHLIGHTED);     // test left-top
        assertTrue(map.background[9][7].state == Background.State.HIGHLIGHTED);     // test left-bottom
        assertTrue(map.isPieceMoving(queen, 5, 5));
        map.cancelSelection(map.background[8][8]);
    }

    @Test
    public void testRook() {
        Map map = new Map(new App(), null);
        Rook rook = new Rook(Color.WHITE, 8, 8);
        Pawn pawn = new Pawn(Color.WHITE, 5, 8);
        Camel camel = new Camel(Color.BLACK, 8, 12);
        map.piece[8][8] = rook;
        map.piece[5][8] = pawn;
        map.piece[8][12] = camel;
        map.changedPosition = map.piece[8][8].movingArea(map);
        map.possibleDestination(map.piece[8][8]);
        assertTrue(map.background[8][8].state == Background.State.SELECTED);
        assertFalse(map.background[8][5].state == Background.State.HIGHLIGHTED);    // test being blocked
        assertFalse(map.background[8][4].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[12][8].state == Background.State.CAPTURED);       // test capturing
        assertFalse(map.background[13][8].state == Background.State.HIGHLIGHTED);
        assertTrue(map.background[8][12].state == Background.State.HIGHLIGHTED);    // test backwards
        assertTrue(map.background[4][8].state == Background.State.HIGHLIGHTED);     // test left
        assertTrue(map.background[8][7].state == Background.State.HIGHLIGHTED);     // test forward
        assertTrue(map.isPieceMoving(rook, 12, 8));
        map.cancelSelection(map.background[8][8]);
    }

    /**
    * Interaction
    **/
    @Test
    public void testGetMouseCoordinate() {
        // CELLSIZE = 48
        int[] expected = {1, 1};
        int[] result = Interaction.getMouseCoordinate(48, 60);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetPiece() {
        Map map = new Map(new App(), null);
        ChessPiece piece = new Pawn(Color.BLACK, 3 ,3); // generate a pwan
        map.piece[3][3] = piece;
        ChessPiece result = Interaction.getPiece(map, new int[] {3, 3});
        assertEquals(piece, result);
    }

    @Test
    public void testTimeDisplay() {
        String expected = "02:30";
        String result = Interaction.timeDisplay(150);
        assertEquals(expected, result);
    }

    /**
     * Background
     **/
    @Test
    public void testSetState() {
        App app = new App();
        Map map = new Map(app, null);
        map.background[0][0].setState(Background.State.HIGHLIGHTED);
        assertEquals(Background.State.HIGHLIGHTED, map.background[0][0].state);
    }

    /**
     * Map
     **/
    @Test
    public void testLoadingBoard() {
        App app = new App();
        Map map = new Map(app, null);
        assertNotNull(map.background);
        assertEquals(14, map.background.length);
        assertEquals(14, map.background[0].length);
        // Check that the board is properly initialized
        for (int i = 0; i <14; i++) {
            for (int j = 0; j < 14; j++) {
                assertNotNull(map.background[i][j]);
            }
        }
    }
}
