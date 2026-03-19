package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.players.ConsolePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JInput extends JTextField {
    private final GameEngine gameEngine;
    private final int playerNumber;
    private final ConsolePlayer player;

    public JInput(ConsolePlayer player, int playerNumber) {
        this.gameEngine = GameEngine.getInstance();
        this.playerNumber = playerNumber;
        this.player = player;
        build();
    }

    private void build(){
        this.addActionListener((ActionEvent e) -> {
            String command = JInput.this.getText();
            try {
                player.executeCommand(command);
                JInput.this.setText("");
            } catch (ConsolePlayer.CommandException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid command or error", JOptionPane.ERROR_MESSAGE);
            }
        });
        String placeholder = "Entrez votre coup (ex: e2 e4)";
        JInput.this.setForeground(Color.GRAY);
        JInput.this.setText(placeholder);
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (JInput.this.getText().equals(placeholder)) {
                    JInput.this.setText("");
                    JInput.this.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (JInput.this.getText().isEmpty()) {
                    JInput.this.setForeground(Color.GRAY);
                    JInput.this.setText(placeholder);
                }
            }
        });
    }
}