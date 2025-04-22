package engine.handler;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Board;
import engine.board.Position;
import engine.move.MoveCalculator;
import engine.piece.*;
import java.util.Map;


/**
 * Class responsible for handling the logic of moving chess pieces on the board.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class MoveHandler {

    private Board board; // The chessboard instance
   private boolean isBlackTurn; // Flag to track if it's the black player's turn
    private Position from; // The starting position of the move
    private Position to;  // The destination position of the move
    private Piece lastPieceMoved; // Tracks the last piece that was moved


    /**
     * Constructor for MoveHandler.
     *
     * @param board The chessboard instance on which the game is played.
     */
    public MoveHandler(Board board) {
        this.board = board;
    }

    /**
     * Sets the game board.
     *
     * @param board The chessboard to be set.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the current turn status for the black player.
     *
     * @return True if it's black's turn, otherwise false.
     */
    public boolean getBlackTurn() {
        return isBlackTurn;
    }

    /**
     * Updates the turn, switching between players.
     */
    public void updateTurn(){
        isBlackTurn = !isBlackTurn;
    }

    /**
     * Sets the last piece that was moved based on the 'to' position.
     */
    public void setLastPieceMoved() {
        this.lastPieceMoved = board.getPiece(to);
    }


    /**
     * Sets the 'from' position.
     *
     * @param from The position from which a piece is being moved.
     */
    public void setFrom(Position from) {
        this.from = from;
    }

    /**
     * Sets the 'to' position.
     *
     * @param to The position to which a piece is being moved.
     */
    public void setTo(Position to) {
        this.to = to;
    }

    /**
     * Gets the 'from' position.
     *
     * @return The position from which a piece is being moved.
     */
    public Position getFrom() {
        return from;
    }

    /**
     * Gets the 'to' position.
     *
     * @return The position to which a piece is being moved.
     */
    public Position getTo() {
        return to;
    }

    /**
     * Gets the current player's color.
     *
     * @return The color of the current player.
     */
    public PlayerColor currentPlayer(){
        return isBlackTurn ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    /**
     * Determines the opponent player's color.
     *
     * @param color The player color.
     * @return The color of the opponent player.
     */
    public PlayerColor opponentPlayer(PlayerColor color){
        return color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    /**
     * Initializes the turn, setting it to white's turn at the start.
     */
    public void initializeTurn(){
        isBlackTurn = false;
    }

    /**
     * Handles the logic for castling, a special move in chess involving the king and a rook.
     *
     * @return True if castling is successfully performed, false otherwise.
     */
    public boolean castle()
    {
        // true if kingside castling
        boolean smallCastlingAsked = to.getX() == 6;

        Position futurPosition = new Position(smallCastlingAsked ? 6 : 2 , currentPlayer() == PlayerColor.WHITE ? 0 : 7);
        Board simulationBoard = new Board(board);
        simulationBoard.move(from, futurPosition);

        if (isPlayerCheck(simulationBoard, futurPosition)){
            return false;
        }
        castling(smallCastlingAsked);
        return true;
    }

    /**
     * Performs the castling move on the board.
     *
     * @param smallCastlingAsked True if kingside castling, false if queenside.
     */
    private void castling(boolean smallCastlingAsked){
        int y = currentPlayer() == PlayerColor.WHITE ? 0 : 7;
        int kingX = smallCastlingAsked ? 6 : 2;
        int rookX = smallCastlingAsked ? 5 : 3;
        int rookXFrom = smallCastlingAsked ? 7 : 0;

        Position kingFrom = new Position(4, y);
        Position kingTo = new Position(kingX, y);
        Position rookFrom = new Position(rookXFrom, y);
        Position rookTo = new Position(rookX, y);
        board.add(kingTo, board.getPiece(kingFrom));
        board.add(rookTo, board.getPiece(rookFrom));
        board.remove(kingFrom);
        board.remove(rookFrom);
    }

    /**
     * Retrieves the position and piece of the king for a given color.
     *
     * @param color The color of the king to find.
     * @return The position and piece of the king, or null if not found.
     */
    public Map.Entry<Position, Piece> getKing(PlayerColor color){
        for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()){
            if (entry.getValue() instanceof King king && king.getColor() == color){
                return entry;
            }
        }
        return null;
    }

    /**
     * Checks if a player is in check at a given position.
     *
     * @param positionOfKingToCheck The position to check for check.
     * @return True if the player is in check, false otherwise.
     */
    public boolean isPlayerCheck(Position positionOfKingToCheck){
        return isPlayerCheck(board, positionOfKingToCheck);
    }
    private boolean isPlayerCheck(Board board, Position positionOfKingToCheck){
        Piece kingToCheck = board.getPiece(positionOfKingToCheck);
        return board.getBoard().entrySet().stream()
                .anyMatch(entry -> kingToCheck instanceof King
                        && entry.getValue().getColor() == opponentPlayer(kingToCheck.getColor())
                        && isCheck(entry.getKey(), positionOfKingToCheck));
    }

    /**
     * Determines if castling is possible under the current conditions.
     *
     * @return True if castling is allowed, false otherwise.
     */
    public boolean isCastlePossible(){
        // Need to determine if it's kingside or queenside castling (right or left rook)
        int rookX = (to.getX() < from.getX() ? 0 : 7);
        int rookY = (currentPlayer() == PlayerColor.WHITE? 0 : 7);

        Piece first = board.getPiece(from);
        Position rookPosition = new Position(rookX, rookY); // Find the position of the rook
        Piece second = board.getPiece(rookPosition);

        return first instanceof King king && second instanceof Rook rook
                && king.getFirstMove() && rook.getFirstMove()
                && !collisionExist(from, rookPosition) && !isPlayerCheck(from);
    }

    /**
     * Determines if a move results in a check.
     *
     * @param from The starting position of the move.
     * @param to   The ending position of the move.
     * @return True if the move results in a check, false otherwise.
     */
    public boolean isCheck(Position from, Position to){
        return (board.getPiece(from).isMoveLegal(from, to) || canPawnEat(from, to)) && !collisionExist(from, to);
    }

    /**
     * Handles the en passant move, a special pawn capture.
     *
     * @return True if en passant is performed, false otherwise.
     */
    public boolean priseEnPassant() {
        Piece piece = board.getPiece(from);
        if (piece == null || currentPlayer() != piece.getColor()) return false;
        int coeff = MoveCalculator.getCoef(currentPlayer());
        int epX = to.getX();
        int epY = to.getY() - coeff;
        Position epPosition = new Position(epX, epY);
        Piece enPassanPiece = board.getPiece(epPosition);
        if (enPassanPiece instanceof Pawn && lastPieceMoved == enPassanPiece && enPassanPiece.getColor() == opponentPlayer(currentPlayer())){
            board.remove(epPosition);
            board.move(from, to);
            return true;
        }
        return false;
    }

    /**
     * Determines if a move can be legally made.
     *
     * @return True if the move is legal, false otherwise.
     */
    public boolean isMovementPossible() {
        if (isPlayerTurn(board.getPiece(from))){
            return false;
        }
        if (canPawnEat(from, to)) return true;

        if (isMoveLegal(from, to)) {
            return false;
        }
        if(isSameColor(to)){
            return false;
        }
        if(collisionExist(from, to)){
            return false;
        }
        return true;
    }

    /**
     * Checks if the selected piece belongs to the player whose turn it is.
     *
     * @param selectedPiece The piece selected for movement.
     * @return True if it's the correct player's turn, false otherwise.
     */
    private boolean isPlayerTurn(Piece selectedPiece) {
        if(selectedPiece == null) return false;
        return selectedPiece.getColor()  == (isBlackTurn? PlayerColor.WHITE : PlayerColor.BLACK);
    }

    /**
     * Determines if a pawn can capture another piece on its move.
     *
     * @param from The starting position of the pawn.
     * @param to   The ending position of the pawn.
     * @return True if the pawn can capture, false otherwise.
     */
    private boolean canPawnEat(Position from, Position to) {
        if ((from.getY() - to.getY()) * MoveCalculator.getCoef(board.getPiece(from).getColor()) > 0) return false;
        if ( board.getPiece(from) instanceof Pawn
                && board.getPiece(to) != null
                && MoveCalculator.isDiagonal(from, to)
                && MoveCalculator.getDistance(from, to) == 1 ){

            return  board.getPiece(to).getColor() != board.getPiece(from).getColor();
        }
        return false;
    }

    /**
     * Checks if a move is legal based on the rules of chess.
     *
     * @param from The starting position of the move.
     * @param destination The ending position of the move.
     * @return True if the move is legal, false otherwise.
     */
    private boolean isMoveLegal(Position from, Position destination) {
        return !board.getPiece(from).isMoveLegal(from, destination);
    }

    /**
     * Checks if a move is to a position occupied by a piece of the same color.
     *
     * @param to The position to check.
     * @return True if the destination has a piece of the same color, false otherwise.
     */
    private boolean isSameColor(Position to){
        Piece piece = board.getPiece(to);
        if (piece == null) {
            return false;
        }
        return board.getPiece(from).getColor() == piece.getColor();
    }

    /**
     * Checks for any pieces between the start and end positions of a move.
     *
     * @param from The starting position of the move.
     * @param to   The ending position of the move.
     * @return True if there is a collision, false otherwise.
     */
    private boolean collisionExist(Position from, Position to) {
        if (board.getPiece(from).getType() == PieceType.KNIGHT) return false;
        if (board.getPiece(from) instanceof Pawn pawn
                && pawn.canMoveAhead(from, to)
                && board.getPiece(to) != null) return true;

        Position[] way = MoveCalculator.getPathBetweenPositions(from, to);
        if (way == null) {
            return false;
        }
        for (Position p : way){
            if (board.getEntry(p).getValue() != null){
                return true;
            }
        }
        return false;
    }
}