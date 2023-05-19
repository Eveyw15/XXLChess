# XXLChess Design Report

To build a chess game application, in my program, I divided the function in three main parts: `App`, `Map` and `ChessBoard`.

`App` is the main class for application, providing setup, draw and get mouse and keyboard input function and `Mousepressed` method achieved the logic of man-machine/player games.

To implement the functions in `App` better, I wrote another class `Interaction` to get the coordinate and piece, draw images and convert time to make the program simpler.

Class `LoadImage` load all pictures of chess pieces and resize them for using them conveniently.

Class `Background` can fill the tile's colour according to its state and it will be called in `Map`.

For `Map`, it is used to draw board and pieces and update them in time. `draw()` draws board and pieces every frame to ensure update the images the first time. Inside the piece iteration in `draw()`, it will check if the king is in check and execute flashing through `frameCount` from `PApplet`. Method `selectedPiece` and `possibleDestination` work together to find the piece's movable coordinate and update its background state. Then `isPieceMoving`, `isMoveValid` and `cancelSelection` execute the movement of the piece and update the colour on board. Besides, `bestMovement` realizes the movement of AI while `isCheckmate` check whether the player is win.

For `ChessPiece`, it is an abstract class that all chess pieces classes inherit from it. It sets their attributes like coordinates and provides an abstract method `movingArea` to get all possible positions according to the rule and call `possibleDestination` to ensure the destination is movable. And in specific chess piece classes, they will return different coordinates according to their own type.
