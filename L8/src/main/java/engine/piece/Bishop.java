package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;
import engine.move.MoveCalculator;

/**
 * Class defining a Bishop piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Bishop extends Piece {
    /**
     * Constructor for Bishop.
     *
     * @param color The color of the piece.
     */
    public Bishop(PlayerColor color) {
        super(color, PieceType.BISHOP);
    }

    /**
     * Determines if the move is legal.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return true if the move is legal.
     */
    @Override
    public boolean isMoveLegal(Position from, Position to) {
        return MoveCalculator.isDiagonal(from, to);
    }
}
