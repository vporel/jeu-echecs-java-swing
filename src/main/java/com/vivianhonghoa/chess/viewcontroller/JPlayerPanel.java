package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.GameEngine;

import javax.swing.*;
import java.awt.*;

public class JPlayerPanel extends JPanel {

    private static final int WIDTH = 200;

    private final int playerNumber;
    private final GameEngine gameEngine;

    public JPlayerPanel(int playerNumber, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.gameEngine = gameEngine;
        build();
    }

    private String getPlayerName(){
        return "Joueur " + playerNumber;
    }

    private String getRemainingTimeStr(){
        int remainingTime = gameEngine.getPlayerRemainingTime(playerNumber);
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void build(){
        JLabel jPlayerName = createCenteredLabel(getPlayerName());
        jPlayerName.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Colors.PRIMARY));
        JLabel jRemainingTimeLabel = createCenteredLabel("Temps restant");
        JLabel jRemainingTimeValue = createCenteredLabel(getRemainingTimeStr());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jPlayerName);
        this.add(jRemainingTimeLabel);
        this.add(jRemainingTimeValue);
        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(WIDTH, 0));
    }

    private JLabel createCenteredLabel(String text){
        JLabel jLabel = new JLabel(text, SwingConstants.CENTER);
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension pref = jLabel.getPreferredSize();
        jLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));
        return jLabel;
    }
}
