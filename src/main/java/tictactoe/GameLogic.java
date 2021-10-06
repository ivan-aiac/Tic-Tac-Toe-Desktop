package tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameLogic {

    public static final char X = 'X';
    public static final char O = 'O';
    public static final char EMPTY_CELL = ' ';
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameState gameState;
    private final Board board;
    private final char[][] cells;
    private final Logger logger;

    public GameLogic(Board board) {
        this.board = board;
        logger = Logger.getLogger(GameLogic.class.getSimpleName());
        cells = new char[3][3];
        reset();
    }

    public void nextMove() {
        if (gameState == GameState.IN_PROGRESS) {
            currentPlayer.nextMove(this);
        }
    }

    public void start() {
        if (playerO == null || playerX == null) {
            logger.warning("Cannot start game without players\n");
            throw new RuntimeException("Cannot start game without players");
        }
        if (gameState != GameState.NOT_STARTED) {
            logger.warning("Game has already started\n");
        } else {
            currentPlayer = playerX;
            gameState = GameState.IN_PROGRESS;
            currentPlayer.nextMove(this);
        }
    }

    public void clickBoardButton(Position position) {
        board.clickButton(position);
    }

    public void markCell(Position position) {
        logger.info(String.format("Player %c marked cell [%d, %d]\n", currentPlayer.getToken(), position.getRow(), position.getCol()));
        cells[position.getRow()][position.getCol()] = currentPlayer.getToken();
        gameState = checkBoard(cells);
        logger.info(String.format("%s\n", gameState.toString()));
        if (gameState.equals(GameState.IN_PROGRESS)) {
            currentPlayer = currentPlayer == playerX ? playerO : playerX;
        }
    }

    public String getPlayerToken() {
        return Character.toString(currentPlayer.getToken());
    }

    public String getPlayerName() {
        return currentPlayer.getName();
    }

    public GameState checkBoard(char[][] cells) {
        // Check for winners
        for (WinningLine line: WinningLine.values()) {
            int sum = 0;
            for (Position pos: line.getPositions()) {
                sum += cells[pos.getRow()][pos.getCol()];
            }
            if (sum == 3 * X) {
                return GameState.X_WON;
            }
            if (sum == 3 * O) {
                return GameState.O_WON;
            }
        }
        // Game in progress if there are empty cells
        if (!getEmptyCellPositions().isEmpty()) {
            return GameState.IN_PROGRESS;
        }
        // Draw otherwise
        return GameState.DRAW;
    }

    public List<Position> getEmptyCellPositions() {
        return Arrays.stream(Position.values())
                .filter(pos -> cells[pos.getRow()][pos.getCol()] == EMPTY_CELL)
                .collect(Collectors.toList());
    }

    public void reset() {
        Arrays.fill(cells[0], EMPTY_CELL);
        Arrays.fill(cells[1], EMPTY_CELL);
        Arrays.fill(cells[2], EMPTY_CELL);
        gameState = GameState.NOT_STARTED;
        currentPlayer = null;
        playerX = null;
        playerO = null;
    }

    public GameState getGameState() {
        return gameState;
    }

    public char[][] getCells() {
        return cells;
    }

    public void setPlayers(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
    }
}
