package tictactoe;

public enum Position {
    A3(0, 0), B3(0, 1), C3(0, 2),
    A2(1, 0), B2(1, 1), C2(1, 2),
    A1(2, 0), B1(2, 1), C1(2, 2);

    private final int row;
    private final int col;

    Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
