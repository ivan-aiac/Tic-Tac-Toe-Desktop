package tictactoe;

import javax.swing.*;
import java.util.logging.Logger;

import static tictactoe.GameLogic.*;

public class MinMaxPlayer extends Player {

    private final Logger logger;
    private final boolean isMaximizing;

    public MinMaxPlayer(char token, String name) {
        super(token, name);
        isMaximizing = token == X;
        logger = Logger.getLogger(MinMaxPlayer.class.getSimpleName());
    }

    @Override
    public void nextMove(GameLogic gameLogic) {
        SwingUtilities.invokeLater(() -> {
            try{
                logger.info("Roboto is thinking...");
                Thread.sleep(1000);
                Position bestMove = null;
                char[][] board = gameLogic.getCells();
                int bestScore = isMaximizing ? -1000 : 1000;
                for (Position position : gameLogic.getEmptyCellPositions()) {
                    board[position.getRow()][position.getCol()] = getToken();
                    int score = minimax(board, !isMaximizing, gameLogic);
                    board[position.getRow()][position.getCol()] = EMPTY_CELL;
                    if (isMaximizing) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = position;
                        }
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove = position;
                        }
                    }
                }
                logger.info(bestMove.toString());
                gameLogic.clickBoardButton(bestMove);
            }catch (InterruptedException e) {
                logger.warning(e.getMessage());
            }
        });
    }

    private int minimax(char[][] board, boolean isMaximizing, GameLogic gameLogic) {
        GameState gameState = gameLogic.checkBoard(board);
        switch (gameState) {
            case IN_PROGRESS:
                break;
            case X_WON:
                return 10;
            case O_WON:
                return -10;
            default:
                return 0;
        }
        int bestScore = isMaximizing ? -1000 : 1000;
        for (Position position: gameLogic.getEmptyCellPositions()) {
            board[position.getRow()][position.getCol()] = isMaximizing ? X : O;
            int score = minimax(board, !isMaximizing, gameLogic);
            board[position.getRow()][position.getCol()] = EMPTY_CELL;
            bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
        }
        return bestScore;
    }
}
