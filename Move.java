package CheckersFramework;

import java.util.Objects;

/**
 *
 * @author Hendrik
 */
public class Move {

    private final Point start;
    private final Direction direction;
    private final int scalar;

    /**
     * @param start the starting point of the move
     * @param direction the direction of the move
     * @param length the length of the move (scalar)
     */
    public Move(Point start, Direction direction, int length) {
        this.start = start;
        this.direction = direction;
        this.scalar = length;
    }

    /**
     * @return the delta on the x-axis
     */
    public int getDeltaX() {
        return this.direction.getDeltaX() * scalar;
    }

    /**
     * @return the delta on the y-axis
     */
    public int getDeltaY() {
        return this.direction.getDeltaY() * scalar;
    }

    /**
     * @return the starting point
     */
    public Point getStart() {
        return start;
    }

    /**
     * @return the scalar
     */
    public int getScalar() {
        return scalar;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Check for equality with another object.
     *
     * @param o the object to check for equality
     * @return whether object o is equal to this point
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        } else {
            Move m = (Move) o;
            return start.equals(m.getStart());
        }
    }

    /**
     * Standard hash code implementation.
     *
     * @return a hash code for this move
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.start);
        hash = 31 * hash + Objects.hashCode(this.direction);
        hash = 31 * hash + this.scalar;
        return hash;
    }

}
