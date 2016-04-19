package checkers;

/**
 *
 * @author Hendrik
 */
public enum Direction {
    NorthEeast(1, -1),
    SouthEeast(1, 1),
    SouthWest(-1, 1),
    NorthWest(-1, -1);

    private final int deltaX;
    private final int deltaY;

    /**
     * @param dx the deltaX
     * @param dy the deltaY
     */
    private Direction(int dx, int dy) {
        this.deltaX = dx;
        this.deltaY = dy;
    }

    /**
     * @return the deltaX
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * @return the deltaY
     */
    public int getDeltaY() {
        return deltaY;
    }

}
