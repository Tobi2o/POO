package engine.piece;

import chess.ChessView;
import chess.PieceType;

/**
 * This class represents a choice of a piece type in the chess game.
 * It implements the UserChoice interface from the ChessView.
 */
public class PieceTypeChoice implements ChessView.UserChoice {
    // The piece type of this choice
    private final PieceType pieceType;

    /**
     * Constructor for PieceTypeChoice.
     * @param pieceType The piece type of this choice.
     */
    public PieceTypeChoice(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * Getter for the piece type of this choice.
     * @return The piece type of this choice.
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * This method is overridden from the UserChoice interface.
     * It returns the string representation of the piece type of this choice.
     * @return The string representation of the piece type of this choice.
     */
    @Override
    public String textValue() {
        return pieceType.toString();
    }
}