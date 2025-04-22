package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import java.util.Objects;

/**
 * Class representing the pieces positions.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class Position {
    public static final int MIN_COORDINATE = 0; // Minimum valid coordinate on the chessboard
    public static final int MAX_COORDINATE = 7; // Maximum valid coordinate on the chessboard
    private final int x; // X-coordinate
    private final int y; // Y-coordiante

    /**
     * Constructor for the Position class.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x-coordinate.
     *
     * @return The x-coordinate of this position.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y-coordinate.
     *
     * @return The y-coordinate of this position.
     */
    public int getY() {
        return y;
    }

    /**
     * Implementation of the hashCode method.
     *
     * @return The hashCode of the instantiated object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Compare the attributes of the instantiated position with the one passed as a parameter.
     *
     * @param other The position to compare.
     * @return True if the attributes are identical.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (other instanceof Position position) {
            return x == position.x && y == position.y;
        }
        return false;
    }

    /**
     * Determines the initial positions of a specific piece type for a given color.
     *
     * @param type  The type of the piece.
     * @param color The color of the piece.
     * @return An array of initial positions for the specified piece type and color.
     */
    public static Position[] initialPosition(PieceType type, PlayerColor color) {
        final int PAWN_ROW_BLACK = 6;
        final int PAWN_ROW_WHITE = 1;
        int pawnRow = color == PlayerColor.BLACK ? PAWN_ROW_BLACK : PAWN_ROW_WHITE;
        int backRow = color == PlayerColor.BLACK ? MAX_COORDINATE : MIN_COORDINATE;

        return switch (type) {
            case PAWN -> createRow(pawnRow);
            case ROOK, KNIGHT, BISHOP, QUEEN, KING -> createSpecificPositions(type, backRow);
        };
    }

    /**
     * Creates positions for specific pieces like rooks, knights, bishops, queen, and king.
     * These positions depend on the piece type and the row where they are initially placed.
     *
     * @param type The type of the chess piece.
     * @param row  The row where the piece is to be placed initially.
     * @return An array of positions for the specified piece type in the given row.
     */

    private static Position[] createSpecificPositions(PieceType type, int row) {
        return switch (type) {
            case ROOK -> new Position[]{new Position(0, row), new Position(7, row)};
            case KNIGHT -> new Position[]{new Position(1, row), new Position(6, row)};
            case BISHOP -> new Position[]{new Position(2, row), new Position(5, row)};
            case QUEEN -> new Position[]{new Position(3, row)};
            case KING -> new Position[]{new Position(4, row)};
            default -> throw new IllegalArgumentException("Invalid piece type for specific positions");
        };
    }


    /**
     * Creates a row of positions for pawns.
     * This is used to initialize the positions of all pawns for a given color.
     *
     * @param row The row number where pawns are to be placed.
     * @return An array of positions representing a row on the chessboard.
     */
    private static Position[] createRow(int row) {
        Position[] positions = new Position[(int) Board.SIZE];
        for (int i = 0; i < (int) Board.SIZE; ++i) {
            positions[i] = new Position(i, row);
        }
        return positions;
    }


    /**
     * Check if a position is valid on the chessboard.
     *
     * @return True if the position is on the chessboard.
     */
    public boolean isValidPosition() {
        return isValidCoordinate(x) && isValidCoordinate(y);
    }


    /**
     * Implementation of the toString() method.
     *
     * @return Position in the format "x: <x> - y: <y>".
     */
    @Override
    public String toString() {
        return String.format("Position[x=%d, y=%d]", x, y);
    }



    /**
     * Validates a single coordinate to ensure it's within the chessboard limits.
     *
     * @param coordinate The coordinate to validate (either x or y).
     * @return True if the coordinate is within the valid range, otherwise false.
     */
    private boolean isValidCoordinate(int coordinate) {
        return coordinate >= MIN_COORDINATE && coordinate <= MAX_COORDINATE;
    }
}
