package CheckersFramework;

import static CheckersFramework.Color.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A Checkers game with international rules.
 *
 * @author Hendrik
 */
public class Game {

    private static final int BOARD_SIZE = 10;
    private final Board board;
    private final MoveGetter input;
    private final Collection<View> views;
    private final Set<Point> startingPoints;
    private Status status;

    public Game(MoveGetter input) {
        this.board = new Board(BOARD_SIZE);
        this.input = input;
        this.views = new LinkedList<>();
        this.startingPoints = new HashSet<>();
        this.status = Status.TurnWhitePlayer;
    }

    public void start() {
        initializeBoard();
        /* **** TODO **** */
        // manage game status
        // get moves - input.getMove()
        // validate moves - isValid(Move m)
        // execute moves
        // update views - updateViews()
    }

    private boolean isValid(Move m) {
        /* **** TODO **** */
        // check if piece at starting point of m is of right color - hasRightColor(Point p)
        // check if player can capture a piece
        // check if move does capture a piece
        // check if the endpoint is on the board
        // check if there's room at the endpoint
        return false;
    }

    private void initializeBoard() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (x % 2 != y % 2) {
                    if (y <= 4) {
                        board.setPiece(new Point(x, y), Piece.Black);
                    } else if (y >= 7) {
                        board.setPiece(new Point(x, y), Piece.White);
                    }
                }
            }
        }
    }

    private void calcStartingPoints() {
        startingPoints.clear();
        Point p;
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                p = new Point(x, y);
                if (activePieceAt(p)) {
                    startingPoints.add(p);
                }
            }
        }
    }

    private boolean activePieceAt(Point p) {
        switch (status) {
            case TurnBlackPlayer:
                return board.getPiece(p).getColor() == Black;
            case TurnWhitePlayer:
                return board.getPiece(p).getColor() == White;
            default:
                return false;
        }
    }

    private boolean isForward(Direction d) {
        switch (d) {
            case NorthEeast:
            case NorthWest:
                return status == Status.TurnWhitePlayer;
            default:
                return status == Status.TurnBlackPlayer;
        }
    }

    /**
     * Check if a move is a king move.
     *
     * @param m the move to check
     * @return whether the move is a king move
     */
    private boolean isKingMove(Move m) {
        int ownPieces = 0;
        int opponentPieces = 0;
        Point p;
        for (int i = 1; i < m.getScalar(); i++) {
            p = new Point(
                    m.getStart().getX() + m.getDirection().getDeltaX() * i,
                    m.getStart().getY() + m.getDirection().getDeltaY() * i
            );
            if (!board.isEmpty(p)) {
                if (activePieceAt(p)) {
                    ownPieces++;
                } else {
                    opponentPieces++;
                }
            }
        }
        p = new Point(
                m.getStart().getX() + m.getDeltaX(),
                m.getStart().getY() + m.getDeltaY()
        );
        return board.isEmpty(p)
                && ownPieces == 0
                && opponentPieces <= 1;
    }

    private void updateViews() {
        views.stream().forEach((v) -> {
            v.setBoard(board);
            v.setStatus(status);
            v.invalidate();
        });
    }

}
