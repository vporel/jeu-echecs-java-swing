package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.engine.History;
import com.vivianhonghoa.chess.model.events.HistoryEvent;
import com.vivianhonghoa.chess.model.events.HistoryObserver;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.players.ConsolePlayer;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class JConsoleFrame extends JFrame {
    private final GameEngine gameEngine;
    private final int playerNumber;
    private final ConsolePlayer player;
    private JTextArea jDisplay;
    private JTextArea jHistory;
    private JTextField jInput;

    public JConsoleFrame(ConsolePlayer player, int playerNumber) {
        this.gameEngine = GameEngine.getInstance();
        this.playerNumber = playerNumber;
        this.player = player;
        setTitle("Chess by Vivian Hong Hoa - Console");
        setSize(400, 500);
        build();
    }

    private void build(){
        this.setLayout(new BorderLayout());
        jInput = new JTextField();
        jInput.addActionListener((ActionEvent e) -> {
            if(playerNumber != gameEngine.getCurrentPlayerNumber()){
                JOptionPane.showMessageDialog(this, "It's not your turn !", "Not your turn", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String command = jInput.getText();
            try {
                player.executeCommand(command);
                jInput.setText("");
                refresh();
            } catch (ConsolePlayer.CommandException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid command", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.add(getDisplayPane(), BorderLayout.CENTER);
        this.add(getHistoryPane(), BorderLayout.EAST);
        this.add(jInput, BorderLayout.SOUTH);

        refresh();
    }

    private void refresh() {
        // Display board
        Board board = gameEngine.getBoard();
        //Top cols letters
        jDisplay.setText("      ");
        for(int col = 0; col < Board.SIZE; col++){
            char colLetter = (char) ('a' + col);
            jDisplay.append(colLetter + " ");
        }
        jDisplay.append("\n");
        for(int row = 0; row < Board.SIZE; row++){
            jDisplay.append("    " + (row + 1) + " ");
            for(int col = 0; col < Board.SIZE; col++){
                Piece piece = board.getPieceAt(row, col);
                String letter = piece == null ? null : String.valueOf(piece.getLetter());
                String cell = piece == null ? "." : piece.getColor() == Piece.Color.WHITE ? letter.toUpperCase() : letter.toLowerCase();
                jDisplay.append(cell + " ");
            }
            jDisplay.append("\n");
        }
    }

    private JScrollPane getDisplayPane(){
        jDisplay = new JTextArea();
        jDisplay.setEditable(false);
        jDisplay.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane jDisplayScroll = new JScrollPane(jDisplay);

        return jDisplayScroll;
    }

    private JScrollPane getHistoryPane(){

        jHistory = new JTextArea();
        jHistory.setEditable(false);
        jHistory.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JPanel jHistoryWrapper = new JPanel(new BorderLayout(5, 5));
        jHistoryWrapper.add(new JLabel("History"), BorderLayout.NORTH);
        jHistoryWrapper.add(jHistory, BorderLayout.CENTER);
        JScrollPane jHistoryScroll = new JScrollPane(jHistoryWrapper);
        JComponentHelper.setFixedWidth(jHistoryScroll, 100);

        //History events
        History history = gameEngine.getPlayerContext(playerNumber).history();
        history.addObserver(new HistoryObserver() {
            @Override
            public void onChange(HistoryEvent event) {
                String historyText = "";
                List<String> moves = history.getFormattedList();
                for(int i = 0; i < moves.size(); i++){
                    String move = (i + 1) + ". " + moves.get(i);
                    historyText += move + "\n";
                }
                jHistory.setText(historyText);
            }
        });

        return jHistoryScroll;
    }
}