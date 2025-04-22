package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;
import engine.move.MoveCalculator;

/**
 * Class defining a Rook piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Rook extends PieceExtend {
    /**
     * Constructor for Rook.
     *
     * @param color The color of the piece.
     */
    public Rook(PlayerColor color) {
        super(color, PieceType.ROOK);
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
        return MoveCalculator.isStraight(from, to);
    }
}
