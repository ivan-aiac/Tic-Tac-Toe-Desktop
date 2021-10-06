package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.List;

import static tictactoe.GameLogic.*;
import static tictactoe.GameState.*;

public class Board {

    private final JMenuBar gameMenuBar;
    private final JPanel boardPanel;
    private final JButton[][] cells;
    private final JButton stateButton;
    private final JButton playerOneButton;
    private final JButton playerTwoButton;
    private final JLabel gameStateLabel;
    private final GameLogic gameLogic;
    private final MessageFormat turn;
    private final MessageFormat wins;

    public Board(){
        boardPanel = new JPanel(new BorderLayout());
        gameMenuBar = new JMenuBar();
        cells = new JButton[3][3];
        stateButton = new JButton();
        gameStateLabel = new JLabel();
        playerTwoButton = new JButton();
        playerOneButton = new JButton();
        gameLogic = new GameLogic(this);
        initMenuBar();
        initCellPanel();
        initMenuPanel();
        initGameStatePanel();
        turn = new MessageFormat("The turn of {0} Player ({1})");
        wins = new MessageFormat("The {0} Player ({1}) wins");
    }

    public void clickButton(Position position) {
        cells[position.getRow()][position.getCol()].doClick();
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JMenuBar getGameMenuBar() {
        return gameMenuBar;
    }

    private void initMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3, 1, 1));
        menuPanel.setBackground(new Color(36,36,36));
        // Player Buttons
        ActionListener playerButtonListener = e -> {
            JButton button = (JButton) e.getSource();
            if ("Human".equals(e.getActionCommand())) {
                setPlayerButtonAction(button, "Robot");
            } else {
                setPlayerButtonAction(button, "Human");
            }
        };
        playerOneButton.setName("ButtonPlayer1");
        setPlayerButtonAction(playerOneButton, "Human");
        playerOneButton.addActionListener(playerButtonListener);

        playerTwoButton.setName("ButtonPlayer2");
        setPlayerButtonAction(playerTwoButton, "Human");
        playerTwoButton.addActionListener(playerButtonListener);

        // Start/Reset Button
        stateButton.setName("ButtonStartReset");
        stateButton.setText("Start");
        stateButton.setActionCommand("START");
        stateButton.addActionListener(e -> {
            if ("START".equals(e.getActionCommand())) {
                startGame();
            } else {
                resetBoard();
            }
        });
        // Add Buttons to panel
        menuPanel.add(playerOneButton);
        menuPanel.add(stateButton);
        menuPanel.add(playerTwoButton);

        boardPanel.add(menuPanel, BorderLayout.NORTH);
    }

    private void setPlayerButtonAction(JButton button, String action) {
        button.setActionCommand(action);
        button.setText(action);
    }

    private void startGame() {
        playerOneButton.setEnabled(false);
        playerTwoButton.setEnabled(false);
        setCellsEnabled(true);
        gameLogic.setPlayers(
                PlayerFactory.createPlayer(playerOneButton.getActionCommand(), X),
                PlayerFactory.createPlayer(playerTwoButton.getActionCommand(), O)
        );
        gameLogic.start();
        gameStateLabel.setText(turn.format(new Object[]{gameLogic.getPlayerName(), gameLogic.getPlayerToken()}));
        stateButton.setActionCommand("RESET");
        stateButton.setText("Reset");
    }

    private void resetBoard() {
        playerOneButton.setEnabled(true);
        playerTwoButton.setEnabled(true);
        setCellsEnabled(false);
        if (!gameLogic.getGameState().equals(NOT_STARTED)) {
            for (JButton[] row: cells) {
                for (JButton cell: row) {
                    cell.setText(Character.toString(EMPTY_CELL));
                }
            }
            gameLogic.reset();
            gameStateLabel.setText(NOT_STARTED.toString());
        }
        stateButton.setText("Start");
        stateButton.setActionCommand("START");
    }

    private void initCellPanel() {
        JPanel cellsPanel = new JPanel();
        cellsPanel.setLayout(new GridLayout(3, 3, 1, 1));
        cellsPanel.setBackground(new Color(36,36,36));

        ActionListener cellsListener = e -> {
            Position pos = Position.valueOf(e.getActionCommand());
            JButton cell = cells[pos.getRow()][pos.getCol()];
            if (cell.getText().equals(Character.toString(EMPTY_CELL))) {
                cell.setText(gameLogic.getPlayerToken());
                gameLogic.markCell(pos);
                if (gameLogic.getGameState().equals(IN_PROGRESS)) {
                    gameStateLabel.setText(turn.format(new Object[]{gameLogic.getPlayerName(), gameLogic.getPlayerToken()}));
                    gameLogic.nextMove();
                } else {
                    switch (gameLogic.getGameState()) {
                        case X_WON:
                        case O_WON:
                            gameStateLabel.setText(wins.format(new Object[]{gameLogic.getPlayerName(), gameLogic.getPlayerToken()}));
                            break;
                        case DRAW:
                            gameStateLabel.setText(DRAW.toString());
                            break;
                    }
                    setCellsEnabled(false);
                }
            }
        };

        JButton cell;
        String name;
        Font font = new Font("SansSerif", Font.BOLD, 64);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell = new JButton();
                name = String.format("%c%d", 65 + j, 3 - i);
                cell.setName(String.format("Button%s", name));
                cell.setFont(font);
                cell.setText(Character.toString(EMPTY_CELL));
                cell.setFocusPainted(false);
                cell.setActionCommand(name);
                cell.addActionListener(cellsListener);
                cell.setEnabled(false);
                cellsPanel.add(cell);
                cells[i][j] = cell;
            }
        }
        boardPanel.add(cellsPanel, BorderLayout.CENTER);
    }

    private void initGameStatePanel() {
        JPanel statePanel = new JPanel();
        statePanel.setLayout(new BorderLayout());
        statePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        gameStateLabel.setText(NOT_STARTED.toString());
        gameStateLabel.setName("LabelStatus");
        statePanel.add(gameStateLabel, BorderLayout.LINE_START);
        boardPanel.add(statePanel, BorderLayout.SOUTH);
    }

    private void initMenuBar() {
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setName("MenuGame");

        ActionListener gameModeListener = e -> {
            if (!gameLogic.getGameState().equals(NOT_STARTED)) {
                resetBoard();
            }
            switch (e.getActionCommand()) {
                case "HH":
                    setPlayerButtonAction(playerOneButton, "Human");
                    setPlayerButtonAction(playerTwoButton, "Human");
                    break;
                case "HR":
                    setPlayerButtonAction(playerOneButton, "Human");
                    setPlayerButtonAction(playerTwoButton, "Robot");
                    break;
                case "RH":
                    setPlayerButtonAction(playerOneButton, "Robot");
                    setPlayerButtonAction(playerTwoButton, "Human");
                    break;
                case "RR":
                    setPlayerButtonAction(playerOneButton, "Robot");
                    setPlayerButtonAction(playerTwoButton, "Robot");
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
            }
            startGame();
        };

        List<String> modes = List.of("Human vs Human", "Human vs Robot", "Robot vs Human", "Robot vs Robot");
        List<String> names = List.of("MenuHumanHuman", "MenuHumanRobot", "MenuRobotHuman", "MenuRobotRobot");
        List<String> actions = List.of("HH", "HR", "RH", "RR");

        JMenuItem item;
        for (int i = 0; i < modes.size(); i++) {
            item = new JMenuItem(modes.get(i));
            item.setName(names.get(i));
            item.setActionCommand(actions.get(i));
            item.addActionListener(gameModeListener);
            gameMenu.add(item);
        }
        gameMenu.addSeparator();
        item = new JMenuItem("Exit");
        item.setName("MenuExit");
        item.setActionCommand("EXIT");
        item.addActionListener(gameModeListener);
        gameMenu.add(item);
        gameMenuBar.add(gameMenu);
    }

    private void setCellsEnabled(boolean value) {
        if (value == cells[0][0].isEnabled()) {
            return;
        }
        for (JButton[] row : cells) {
            for (JButton button : row) {
                button.setEnabled(value);
            }
        }
    }

}
