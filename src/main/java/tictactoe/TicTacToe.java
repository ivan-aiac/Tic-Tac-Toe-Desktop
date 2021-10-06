package tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {

    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setTitle("Tic-Tac-Toe");
        setLocationRelativeTo(null);
        Board board = new Board();
        add(board.getBoardPanel(), BorderLayout.CENTER);
        setJMenuBar(board.getGameMenuBar());
        setVisible(true);
    }
}