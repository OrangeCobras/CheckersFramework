package CheckersFramework;

import java.util.LinkedList;

/**
 * A Checkers game with international rules.
 *
 * @author Hendrik
 */
public class Game {

    private final Board board;
    private final MoveGetter input;
    private final LinkedList<View> views;
    private Status status;

    public Game(MoveGetter input) {
        this.board = new Board(10);
        this.input = input;
        this.views = new LinkedList<>();
        this.status = Status.TurnWhitePlayer;
    }

    public void start() {
        /***** TODO *****/
        // inititalize board
        // manage game status
        // get moves
        // validate moves
        // execute moves
        // update views
    }

    private void updateViews() {
        views.stream().forEach((v) -> {
            v.setBoard(board);
            v.setStatus(status);
            v.invalidate();
        });
    }

}
