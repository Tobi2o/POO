package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Position;
import engine.piece.*;
import engine.handler.*;
import java.util.Map.Entry;


/**
 * Class defining the game engine
 * This class implements the ChessController interface
 * It is responsible for managing the game
 * Nathan Rayburn and Harun Ouweis
 */
public class GameEngine implements ChessController {
    private ChessView view; // View that the Controller has to manage
    private MoveHandler moveHandler; // Handler of the moves
    private String message;
    private Board board = new Board();
    private Board boardSnapShot = new Board(); // State of the chessboard at a given moment for returning to a previous situation
    // ------------------------ Constructors ------------------------

    /**
     * Constructor of the class GameEngine
     */
    public GameEngine() {}

    // ------------------------ Public methods ------------------------

    /**
     * checkmate(): This method allows to check if a player is checkmate
     * @param player
     * @return
     */
     private boolean checkmate(PlayerColor player){
         // todo checkmate
        return false;
    }
    /**
     * start(): This method allows to start the game
     * @param view
     */
    @Override
    public void start(ChessView view) {
        this.view = view;
        this.moveHandler = new MoveHandler( board);
        newGame();
        view.startView();
    }

    /**
     * newGame(): Launches a new game
     */
    @Override
    public void newGame() {
        if (view == null) throw new RuntimeException("Error, view is null");
        message = "New game, White player's turn";
        moveHandler.initializeTurn();
        initialize();
        updateView();
    }

    /**
     * move(): Method called when the user requests a move
     * @param fromX the X coordinate of the first click
     * @param fromY the Y coordinate of the first click
     * @param toX   the X coordinate of the second click
     * @param toY   the Y coordinate of the second click
     * @return true if the move was successfully made
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        snapshot(fromX, fromY, toX, toY);
        boardSnapShot = new Board(board);

        if (!moveHandler.getTo().isValidPosition()){
            message = "Invalid position";
            displayMessage();
            return false;
        }
        if(fromX == toX && fromY == toY){
            message = "You can't move to the same position";
            displayMessage();
            return false;
        }

        if (board.getPiece(moveHandler.getFrom()) == null) {
            message = "No pieces were selected";
            displayMessage();
            return false;
        }

        if(moveHandler.isCastlePossible()){
            if (!moveHandler.castle()){
                message = "Castling is not possible, king in check";
                displayMessage();
                return false;
            }
            endOfTurn();
            return true;
        }

        if (moveHandler.priseEnPassant()) {
            endOfTurn();
            return true;
        }

        if(!moveHandler.isMovementPossible()){
            displayMessage();
            return false;
        }

        // todo make this better
        if(board.getPiece(moveHandler.getFrom()) instanceof PieceExtend pieceExtend){
            pieceExtend.setFirstMove();
        }

        board.move(moveHandler.getFrom(), moveHandler.getTo());
        // todo make this better
        if(moveHandler.getTo().getY() == (moveHandler.getBlackTurn()? 0 : 7) && board.getPiece(moveHandler.getTo()).getType() == PieceType.PAWN){
            pawnToPromote();
        }

        if(moveHandler.isPlayerCheck(moveHandler.getKing(moveHandler.currentPlayer()).getKey())) {
            message = "The king is in check";
            playBackSnapshot();
            displayMessage();
            return false;
        }
        PlayerColor opponent = moveHandler.opponentPlayer(moveHandler.currentPlayer());
        if(checkmate(opponent)) {
            askNewGame();
            return false;
        }

        if(moveHandler.isPlayerCheck(moveHandler.getKing(opponent).getKey())) {
            message = opponent.toString() + "'s in check! ";
        }
        else {
            message = opponent + "'s turn";
        }

        endOfTurn();
        return true;
    }

    // ------------------------ Private methods ------------------------

    /**
     * askNewGame(): This method is used to display a request for a new game
     */
    private void askNewGame(){
        PlayerColor op = moveHandler.opponentPlayer(moveHandler.currentPlayer());
        if (view.askUser("Game over", op + " lose the game\nNew Game ?",
                new ChessView.UserChoice() {
                    @Override
                    public String textValue() {
                        return "Yes";
                    }
                }, new ChessView.UserChoice() {
                    @Override
                    public String textValue() {
                        return "No";
                    }
                }).textValue().equals("Yes")) {
            newGame();
        } else {
            System.exit(0);
        }
    }

    /**
     * endOfTurn(): This method finalizes the turn
     */
    private void endOfTurn(){
        moveHandler.updateTurn();
        moveHandler.setLastPieceMoved();
        updateView();
    }

    /**
     * pawnToPromote(): This method allows for pawn promotion
     */
    private void pawnToPromote() {

        ChessView.UserChoice[] choices = new ChessView.UserChoice[]{
                new PieceTypeChoice(PieceType.QUEEN),
                new PieceTypeChoice(PieceType.BISHOP),
                new PieceTypeChoice(PieceType.KNIGHT),
                new PieceTypeChoice(PieceType.ROOK)
        };

        ChessView.UserChoice userChoice = view.askUser("Promotion of pawn",
                "Please, choose a piece", choices);
        PieceType selectedType = ((PieceTypeChoice) userChoice).getPieceType();
        Position to = moveHandler.getTo();
        PlayerColor color =  moveHandler.currentPlayer();
        switch (selectedType){
            case QUEEN -> board.add(to, new Queen(color));
            case BISHOP -> board.add(to, new Bishop(color));
            case KNIGHT -> board.add(to, new Knight(color));
            case ROOK -> board.add(to, new Rook(color));
            default -> throw new RuntimeException("Promotion error");
        }
    }


    /**
     * playBackSnapshot(): This method allows to return to a previous situation
     */
    private void playBackSnapshot(){
        for (Entry<Position, Piece> entry : board.getBoard().entrySet()){
            removePiece(entry);
        }
        for (Entry<Position, Piece> entry : boardSnapShot.getBoard().entrySet()){
            putPiece(entry);
        }
        moveHandler.setBoard(boardSnapShot);
        board = boardSnapShot;
    }

    /**
     * snapshot(): This method allows to take a snapshot of the board
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    private void snapshot(int fromX, int fromY, int toX, int toY) {
        moveHandler.setFrom(new Position(fromX, fromY));
        moveHandler.setTo(new Position(toX, toY));
    }

    /**
     * initialize() : Initialization of the board
     */
    private void initialize() {
        board.initialize();
    }

    /**
     * updateView() : updates the view
     */
    private void updateView(){
        displayMessage();
        clearView();
        putPieces();
    }
    /**
     * displayMessage() : allows displaying a message
     */
    private void displayMessage(){
        displayMessage(message);
    }

    /**
     * displayMessage() : allows displaying a message
     * @param message The message to display
     */
    private void displayMessage(String message) {
        view.displayMessage(message);
    }

    /**
     * clearView() : Allows to clear the pieces on the views
     */
    private void clearView() {
        for (int x = 0; x < Board.SIZE; ++x ){
            for (int y = 0; y < Board.SIZE; ++y){
                view.removePiece(x, y);
            }
        }
    }
    /**
     * putPiece() : Updates the view with the addition of a piece
     * @param entry The key-value includes the position and the piece to add
     */
    private void putPiece(Entry<Position, Piece> entry) {
        putPiece(entry.getKey(), entry.getValue());
    }

    /**
     * putPiece() : Updates the view with the addition of a piece
     * @param position The position where the piece should be added
     * @param piece    The piece to add
     */
    private void putPiece(Position position, Piece piece) {
        if (piece == null) return;
        view.putPiece(piece.getType(), piece.getColor(), position.getX(), position.getY());
    }

    /**
     * putPieces() : Updates the view with the pieces on the board
     */
    private void putPieces() {
        for (Entry<Position, Piece> entry : board.getBoard().entrySet()) {
            putPiece(entry.getKey(), entry.getValue());
        }
    }

    /**
     * removePiece() : Updates the view with the removal of a piece
     * @param entry The key-value includes the position and the piece to remove
     */
    private void removePiece(Entry<Position, Piece> entry) {
        Position position = entry.getKey();
        view.removePiece(position.getX(), position.getY());
    }
}
