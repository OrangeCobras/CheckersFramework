package CheckersFramework;

/**
 *
 * @author Hendrik
 */
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Check for equality with another object.
     *
     * @param o the object to check for equality
     * @return whether the object o is equal to this point
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        } else {
            Point p = (Point) o;
            return x == p.getX()
                    && y == p.getY();
        }
    }

}
