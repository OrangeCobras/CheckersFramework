package checkers;

/**
 *
 * @author Hendrik
 */
public class Move {

    private final Direction direction;
    private final int scalar;

    /**
     * @param direction the direction of the move
     * @param length the length of the move (scalar)
     */
    public Move(Direction direction, int length) {
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

}
