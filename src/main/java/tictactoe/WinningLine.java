package tictactoe;

import java.util.List;

import static tictactoe.Position.*;

public enum WinningLine {

    HOR_TOP(List.of(A3, B3, C3)),
    HOR_MID(List.of(A2, B2, C2)),
    HOR_BOT(List.of(A1, B1, C1)),
    VER_LEFT(List.of(A3, A2, A1)),
    VER_MID(List.of(B3, B2, B1)),
    VER_RIGHT(List.of(C3, C2, C1)),
    DIA_MAIN(List.of(A3, B2, C1)),
    DIA_SIDE(List.of(C3, B2, A1));

    private final List<Position> positions;

    WinningLine(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return positions;
    }
}
