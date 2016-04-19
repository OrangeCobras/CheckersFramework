package checkers;

/**
 *
 * @author Hendrik
 */
public class Board {

    private final Piece[][] board;

    /**
     * @param size the size of the board
     */
    public Board(int size) {
        this.board = new Piece[size][size];
    }

    /**
     * @param p the point to get the piece for
     * @return the piece at point p
     */
    public Piece getPiece(Point p) {
        return this.board[p.getX()][p.getY()];
    }

}
