package tictactoe;

public abstract class Player {
    protected final char token;
    protected final String name;

    protected Player(char token, String name) {
        this.name = name;
        this.token = token;
    }

    public char getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public abstract void nextMove(GameLogic gameLogic);
}
