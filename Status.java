package CheckersFramework;

/**
 *
 * @author Hendrik
 */
public enum Status {
    TurnWhitePlayer {
        @Override
        public Status invert() {
            return TurnBlackPlayer;
        }
    },
    TurnBlackPlayer {
        @Override
        public Status invert() {
            return TurnWhitePlayer;
        }
    },
    GameEnded {
        @Override
        public Status invert() {
            return this;
        }
    };

    /**
     * Invert this status.
     *
     * @return the inverse of this
     */
    public abstract Status invert();

}
