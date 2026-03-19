package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.players.ConsolePlayer;

import javax.swing.*;
import java.awt.*;

public class JConsoleFrame extends JFrame {

    public JConsoleFrame(ConsolePlayer player, int playerNumber) {
        this.setTitle("Chess by Vivian Hong Hoa - Console");
        setSize(400, 500);
        this.setLayout(new BorderLayout());

        this.add(new JHeader(playerNumber), BorderLayout.NORTH);
        this.add(new JBoardDisplay(), BorderLayout.CENTER);
        this.add(new JHistory(playerNumber), BorderLayout.EAST);
        this.add(new JInput(player, playerNumber), BorderLayout.SOUTH);
    }
}