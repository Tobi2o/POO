package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;
import engine.move.MoveCalculator;

/**
 * Class defining a Queen piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Queen extends Piece {
    /**
     * Constructor for Queen.
     *
     * @param color The color of the piece.
     */
    public Queen(PlayerColor color) {
        super(color, PieceType.QUEEN);
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
        return MoveCalculator.isDiagonal(from, to) || MoveCalculator.isStraight(from, to);
    }
}
