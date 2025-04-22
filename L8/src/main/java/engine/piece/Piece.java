package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;

/**
 * Class defining a game piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public abstract class Piece {
    private final PieceType type;
    private final PlayerColor color;

    /**
     * Constructor for a piece.
     *
     * @param color The color of the piece.
     * @param type  The type of the piece.
     */
    public Piece(PlayerColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Getter for the type of the piece.
     *
     * @return The type of the piece.
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Getter for the color of the piece.
     *
     * @return The color of the piece.
     */
    public PlayerColor getColor() {
        return this.color;
    }

    /**
     * Display information about the piece.
     *
     * @return A String containing displayable elements of the piece.
     */
    @Override
    public String toString() {
        return color.toString() + " " + type.toString();
    }

    /**
     * Determines if a piece's move is allowed.
     *
     * @param from The starting position of the piece.
     * @param to   The destination position of the piece.
     * @return true if the move is legal.
     */
    public abstract boolean isMoveLegal(Position from, Position to);
}
