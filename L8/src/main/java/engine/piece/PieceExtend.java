package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

/**
 * Class extending Piece.
 * Common for all pieces
 * @author Rayburn Nathan, Harun Ouweis
 */
public abstract class PieceExtend extends Piece {
    protected boolean firstMove = true; // Indicates if it's the piece's first move

    /**
     * Reimplementation of the Piece constructor.
     *
     * @param color The color of the piece.
     * @param type  The type of the piece.
     */
    public PieceExtend(PlayerColor color, PieceType type) {
        super(color, type);
    }

    /**
     * Getter for the first move.
     *
     * @return true if it's the first move.
     */
    public boolean getFirstMove() {
        return firstMove;
    }

    /**
     * Setter to set the first move to false during the first move.
     */
    public void setFirstMove() {
        this.firstMove = false;
    }
}
