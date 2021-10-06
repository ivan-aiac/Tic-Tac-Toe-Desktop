package tictactoe;

import java.util.logging.Logger;

public class HumanPlayer extends Player{

    private final Logger logger;

    public HumanPlayer(char token, String name) {
        super(token, name);
        logger = Logger.getLogger(HumanPlayer.class.getSimpleName());
    }

    @Override
    public void nextMove(GameLogic gameLogic) {
        logger.info(String.format("Waiting for Player %c to make a move\n", token));
    }
}
