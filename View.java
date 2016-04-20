package CheckersFramework;

/**
 *
 * @author Hendrik
 */
public interface View {

    public void setBoard(Board board);

    public void setStatus(Status status);

    /**
     * Invalidate the view.
     *
     * This means the view needs to be redrawn.
     */
    public void invalidate();

}
