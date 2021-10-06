package tictactoe;

public enum GameState {
    NOT_STARTED("Game is not started"),
    IN_PROGRESS("Game in progress"),
    X_WON("X wins"),
    O_WON("O wins"),
    DRAW("Draw");

    private final String state;

    GameState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
