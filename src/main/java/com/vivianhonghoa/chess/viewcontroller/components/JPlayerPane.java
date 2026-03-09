package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;

public class JPlayerPane extends JPanel {

    private static final int WIDTH = 200;

    private final int playerNumber;
    private final GameEngine gameEngine;

    public JPlayerPane(int playerNumber, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.gameEngine = gameEngine;
        build();
    }

    private String getPlayerName(){
        return "JOUEUR " + playerNumber;
    }

    private String getRemainingTimeStr(){
        Integer remainingTime = gameEngine.getPlayerRemainingTime(playerNumber);
        if(remainingTime == null){
            return "∞";
        }
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void build(){
        JLabel jPlayerName = new JLabel(getPlayerName(), SwingConstants.CENTER);
        JComponentHelper.setBold(jPlayerName);
        JComponentHelper.setFontSize(jPlayerName, 20);
        JLabelWrapper jPlayerNameWrapper = new JLabelWrapper(jPlayerName, true);
        jPlayerNameWrapper.setPaddingVertical(10);
        jPlayerNameWrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Colors.PRIMARY));
        jPlayerNameWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jRemainingTimeLabel = new JLabel("Temps restant", SwingConstants.CENTER);
        JLabelWrapper jRemainingTimeLabelWrapper = new JLabelWrapper(jRemainingTimeLabel, true);
        jRemainingTimeLabelWrapper.setPaddingVertical(10);
        jRemainingTimeLabelWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jRemainingTimeValue = new JLabel(getRemainingTimeStr(), SwingConstants.CENTER);
        JComponentHelper.setFontSize(jRemainingTimeValue, 18);
        JLabelWrapper jRemainingTimeValueWrapper = new JLabelWrapper(jRemainingTimeValue, true);
        jRemainingTimeValueWrapper.setPaddingVertical(5);
        jRemainingTimeValueWrapper.setBackground(Colors.APP_BACKGROUND);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jPlayerNameWrapper);
        this.add(jRemainingTimeLabelWrapper);
        this.add(jRemainingTimeValueWrapper);
        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(WIDTH, 0));
    }
}
