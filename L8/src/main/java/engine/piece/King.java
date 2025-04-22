package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;
import engine.move.*;

/**
 * Class defining a King piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class King extends PieceExtend {
    /**
     * Constructor for King.
     *
     * @param color The color of the piece.
     */
    public King(PlayerColor color) {
        super(color, PieceType.KING);
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
        return MoveCalculator.getDistance(from, to) == 1;
    }
}
