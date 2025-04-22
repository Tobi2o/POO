package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class representing the chessboard.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Board {
    public final static byte SIZE = 8; // Size of the chessboard
    private final Map<Position, Piece> board;

    /**
     * Constructor for the Board class.
     */
    public Board() {
        this.board = new HashMap<>();
    }

    /**
     * Copy constructor for the Board class.
     *
     * @param other Board to copy.
     */
    public Board(Board other) {
        this.board = new HashMap<>(other.board);
    }

    /**
     * Add a piece to the chessboard.
     *
     * @param position Position to place the piece.
     * @param piece    Piece to place.
     */
    public void add(Position position, Piece piece) {
        board.put(position, piece);
    }

    /**
     * Remove the content of a chessboard square.
     *
     * @param position Position to remove.
     */
    public void remove(Position position) {
        board.remove(position);
    }

    /**
     * Move the content from one position on the chessboard to another.
     *
     * @param from Starting position of the piece.
     * @param to   Destination position of the piece.
     */
    public void move(Position from, Position to) {
        Piece piece = board.remove(from);
        if (piece != null) {
            board.put(to, piece);
        }
    }

    /**
     * Determine which piece is at a specific position.
     *
     * @param position Position to inquire about.
     * @return Piece at the position.
     */
    public Piece getPiece(Position position) {
        return board.get(position);
    }

    /**
     * Getter for the chessboard.
     *
     * @return The chessboard as a HashMap<Position, Piece>.
     */
    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    /**
     * Return a Position-Piece association.
     *
     * @param position Position for which to get the association.
     * @return Association in the form of an Entry<Position, Piece>.
     */
    public Entry<Position, Piece> getEntry(Position position) {
        if (position == null) {
            throw new RuntimeException("Position can't be null");
        }
        return new AbstractMap.SimpleEntry<>(position, board.get(position));
    }


    /**
     * Initializes the chessboard with default piece positions.
     * This method clears any existing pieces on the board and then sets up
     * each piece in its standard game start position according to the rules
     * of chess.
     */
    public void initialize() {
        board.clear();
        for (PlayerColor color : PlayerColor.values()) {
            initializeColor(color);
        }
    }

    /**
     * Initializes the pieces of a specific color on the chessboard.
     * This private helper method is called by initialize() and positions
     * all pieces of the given color in their standard starting locations.
     *
     * @param color The color of the pieces to initialize.
     */
    private void initializeColor(PlayerColor color) {
        for (PieceType type : PieceType.values()) {
            for (Position position : Position.initialPosition(type, color)) {
                board.put(position, createPiece(type, color));
            }
        }
    }

    /**
     * Creates a piece of the specified type and color.
     * This method is a factory for creating chess piece objects based on
     * the piece type and color. It is utilized in the piece initialization process.
     *
     * @param type  The type of chess piece to create (e.g., Pawn, Rook).
     * @param color The color of the chess piece (Black or White).
     * @return The created chess piece object.
     */
    private Piece createPiece(PieceType type, PlayerColor color) {
        switch (type) {
            case PAWN: return new Pawn(color);
            case ROOK: return new Rook(color);
            case KNIGHT: return new Knight(color);
            case BISHOP: return new Bishop(color);
            case QUEEN: return new Queen(color);
            case KING: return new King(color);
            default: throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }

}
