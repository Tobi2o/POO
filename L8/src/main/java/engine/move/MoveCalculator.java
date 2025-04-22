package engine.move;

import chess.PlayerColor;
import engine.board.Position;

/**
 * Class representing the possible movement options for pieces.
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class MoveCalculator {


    /**
     * Private static class representing the distance between two positions.
     * It stores the horizontal (dx) and vertical (dy) distance components.
     */
    private static class Distance {
        final int dx;
        final int dy;


        /**
         * Constructor for the Distance class.
         *
         * @param dx The horizontal distance.
         * @param dy The vertical distance.
         */
        Distance(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }


    /**
     * Calculates the absolute distances in both x and y directions between two positions.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return The Distance object containing both x and y components.
     */
    private static Distance getAbsoluteDistances(Position from, Position to) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());
        return new Distance(dx, dy);
    }


    /**
     * Allows for a positive coefficient if the color is white and negative if the color is black.
     *
     * @param playerColor The desired color.
     * @return -1 if moving backward and 1 if moving forward.
     */
    public static int getCoef(PlayerColor playerColor) {
        return playerColor == PlayerColor.WHITE ? 1 : -1;
    }

    /**
     * Determines if the movement is diagonal.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return True if the movement is diagonal.
     */
    public static boolean isDiagonal(Position from, Position to) {
        Distance distance = getAbsoluteDistances(from, to);
        return distance.dx == distance.dy;
    }
    /**
     * Determines if the movement is orthogonal.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return True if the movement is orthogonal.
     */
    public static boolean isStraight(Position from, Position to) {
        Distance distance = getAbsoluteDistances(from, to);
        return distance.dx == 0 || distance.dy == 0;
    }

    /**
     * Get the absolute distance on the X-axis.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return The distance between the start and destination.
     */
    public static int getAbsDistX(Position from, Position to) {
        return Math.abs(getDistX(from, to));
    }

    /**
     * Get the distance on the X-axis.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return The distance between the start and destination.
     */
    public static int getDistX(Position from, Position to) {
        return to.getX() - from.getX();
    }

    /**
     * Get the distance on the Y-axis.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return The distance between the start and destination.
     */
    public static int getDistY(Position from, Position to) {
        return to.getY() - from.getY();
    }

    /**
     * Get the absolute distance on the Y-axis.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return The distance between the start and destination.
     */
    public static int getAbsDistY(Position from, Position to) {
        return Math.abs(to.getY() - from.getY());
    }

    /**
     * Get the distance traveled from 'from' to 'to'.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return The distance traveled between 'from' and 'to', -1 if the distance is not orthogonal or diagonal.
     */
    public static int getDistance(Position from, Position to) {
        Distance distance = getAbsoluteDistances(from, to);

        if (isStraight(from, to)) {
            return distance.dx + distance.dy;
        }
        if (isDiagonal(from, to)) {
            return distance.dx;
        }
        return -1;
    }

    /**
     * Retrieve the positions between 'from' (non-inclusive) and 'to' (non-inclusive).
     * This method is useful for determining the path a piece will traverse, excluding
     * the start and end positions. It's primarily used to check for obstacles in a piece's path.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @return An array of positions between 'from' and 'to', excluding the start and end positions.
     * @throws IllegalArgumentException if the move is neither straight nor diagonal.
     */
    public static Position[] getPathBetweenPositions(Position from, Position to) {
        int dist = getDistance(from, to);
        if (dist <= 1) {
            return new Position[0];
        }

        int xCoef = Integer.compare(to.getX(), from.getX());
        int yCoef = Integer.compare(to.getY(), from.getY());
        Position[] way = new Position[dist - 1];

        if (!isStraight(from, to) && !isDiagonal(from, to)) {
            throw new IllegalArgumentException("Illegal move");
        }

        for (int i = 1; i < dist; ++i) {
            way[i - 1] = new Position(from.getX() + i * xCoef, from.getY() + i * yCoef);
        }
        return way;
    }
}

