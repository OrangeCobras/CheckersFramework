package CheckersFramework;

/**
 *
 * @author Hendrik
 */
public enum Piece {
    Black(Color.Black, false),
    BlackKing(Color.Black, true),
    White(Color.White, false),
    WhiteKing(Color.White, true);

    private final Color color;
    private final boolean king;

    /**
     * @param color the color of the piece
     * @param isKing whether the piece is a king
     */
    private Piece(Color color, boolean isKing) {
        this.color = color;
        this.king = isKing;
    }

    /**
     * Get the crowned version of this piece.
     *
     * @return the crowned version of this
     */
    public Piece crowned() {
        if (color == Color.White) {
            return WhiteKing;
        } else {
            return BlackKing;
        }
    }

    /**
     * @return whether this piece is a king
     */
    public boolean isKing() {
        return king;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

}
