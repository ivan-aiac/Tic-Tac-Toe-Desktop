package tictactoe;

public class PlayerFactory {

    public static Player createPlayer(String type, char token) {
        switch (type) {
            case "Human":
                return new HumanPlayer(token, type);
            case "Robot":
                return new MinMaxPlayer(token, type);
            default:
                throw new RuntimeException("Undefined player type.");
        }
    }
}
