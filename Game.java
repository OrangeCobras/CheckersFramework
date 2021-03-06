package CheckersFramework;

import static CheckersFramework.Color.*;
import static CheckersFramework.Status.*;
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
    private final Set<Move> possibleMoves;
    private Status status;

    public Game(MoveGetter input) {
        this.board = new Board(BOARD_SIZE);
        this.input = input;
        this.views = new LinkedList<>();
        this.startingPoints = new HashSet<>();
        this.possibleMoves = new HashSet<>();
        this.status = Status.TurnWhitePlayer;
    }

    public void start() {
        Move m;
        initializeBoard();
        updateViews();
        while (status != GameEnded) {
            calcPossibleMoves();
            if (possibleMoves.isEmpty()) {
                endGame();
                return;
            }
            do {
                m = input.getMove();
            } while (!possibleMoves.contains(m));
            execute(m);
            endTurn();
            updateViews();
        }
    }

    private void endGame() {
        status = GameEnded;
        updateViews();
    }

    private void endTurn() {
        status = status.invert();
    }

    private void execute(Move m) {
        board.setPiece(m.getEnd(), board.getPiece(m.getStart()));
        for (int s = 0; s < m.getScalar(); s++) {
            board.setPiece(new Point(
                    m.getStart().getX() + s * m.getDirection().getDeltaX(),
                    m.getStart().getY() + s * m.getDirection().getDeltaY()
            ),
                    null
            );
        }
        if (status == TurnBlackPlayer && m.getEnd().getY() == BOARD_SIZE
                || status == TurnWhitePlayer && m.getEnd().getY() == 0) {
            board.setPiece(m.getEnd(), board.getPiece(m.getEnd()).crowned());
        }
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

    private void calcPossibleMoves() {
        calcStartingPoints();
        possibleMoves.clear();
        addCapturingMoves();
        if (possibleMoves.isEmpty()) {
            addNormalMoves();
        }
    }

    private void addCapturingMoves() {
        Point p1;
        Point p2;
        Move m;
        MoveInfo info;
        for (Point p : startingPoints) {
            for (Direction d : Direction.values()) {
                if (board.getPiece(p).isKing()) {
                    for (int i = 1; i < BOARD_SIZE - 1; i++) {
                        m = new Move(p, d, i);
                        info = getInfo(m);
                        if (info.kingMove && info.captures) {
                            possibleMoves.add(m);
                        }
                    }
                } else {
                    p1 = new Point(
                            p.getX() + d.getDeltaX(),
                            p.getY() + d.getDeltaY()
                    );
                    p2 = new Point(
                            p.getX() + 2 * d.getDeltaX(),
                            p.getY() + 2 * d.getDeltaY()
                    );
                    if (board.isEmpty(p2)
                            && !board.isEmpty(p1)
                            && !activePieceAt(p1)) {
                        possibleMoves.add(new Move(p, d, 2));
                    }
                }
            }
        }
    }

    private void addNormalMoves() {
        Point p1;
        Move m;
        MoveInfo info;
        for (Point p : startingPoints) {
            for (Direction d : Direction.values()) {
                if (board.getPiece(p).isKing()) {
                    for (int i = 1; i < BOARD_SIZE - 1; i++) {
                        m = new Move(p, d, i);
                        info = getInfo(m);
                        if (info.kingMove) {
                            possibleMoves.add(m);
                        }
                    }
                } else if (isForward(d)) {
                    p1 = new Point(
                            p.getX() + d.getDeltaX(),
                            p.getY() + d.getDeltaY()
                    );
                    if (board.isEmpty(p1)) {
                        possibleMoves.add(new Move(p, d, 1));
                    }
                }
            }
        }
    }

    private boolean activePieceAt(Point p) {
        switch (status) {
            case TurnBlackPlayer:
                return board.contains(p)
                        && board.getPiece(p).getColor() == Black;
            case TurnWhitePlayer:
                return board.contains(p)
                        && board.getPiece(p).getColor() == White;
            default:
                return false;
        }
    }

    private boolean isForward(Direction d) {
        switch (d) {
            case NorthEeast:
            case NorthWest:
                return status == TurnWhitePlayer;
            default:
                return status == TurnBlackPlayer;
        }
    }

    /**
     * Get information about a move.
     *
     * @param m the move to get information about
     * @return information about the move
     */
    private MoveInfo getInfo(Move m) {
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
        return new MoveInfo(
                board.isEmpty(m.getEnd())
                && ownPieces == 0
                && opponentPieces <= 1,
                opponentPieces == 1
        );
    }

    private void updateViews() {
        views.stream().forEach((v) -> {
            v.setBoard(board);
            v.setStatus(status);
            v.invalidate();
        });
    }

    private class MoveInfo {

        private final boolean kingMove;
        private final boolean captures;

        public MoveInfo(boolean kingMove, boolean captures) {
            this.kingMove = kingMove;
            this.captures = captures;
        }

    }

}
