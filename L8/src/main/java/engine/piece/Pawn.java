package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.Position;
import engine.move.MoveCalculator;

/**
 * Class defining a Pawn piece.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Pawn extends PieceExtend {
    /**
     * Constructor for Pawn.
     *
     * @param color The color of the piece.
     */
    public Pawn(PlayerColor color) {
        super(color, PieceType.PAWN);
    }

    /**
     * Ensures a vertical movement.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return true if the movement is vertical.
     */
    public boolean canMoveAhead(Position from, Position to) {
        int coef = MoveCalculator.getCoef(getColor());
        return (to.getY() - from.getY()) * coef > 0 && to.getX() - from.getX() == 0;
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
        return MoveCalculator.isStraight(from, to) && canMoveAhead(from, to) && MoveCalculator.getDistance(from, to) <= (firstMove ? 2 : 1);
    }
}
