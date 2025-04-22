package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;
import engine.move.MoveCalculator;

/**
 * Class defining a Knight piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Knight extends Piece {
    final private double MAX_DISTANCE = Math.sqrt(1 + 2 * 2); // Exact distance that Knight must follow during a move

    /**
     * Constructor for Knight.
     *
     * @param color The color of the piece.
     */
    public Knight(PlayerColor color) {
        super(color, PieceType.KNIGHT);
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
        return MAX_DISTANCE == Math.hypot(MoveCalculator.getAbsDistX(from, to), MoveCalculator.getAbsDistY(from, to));
    }
}
