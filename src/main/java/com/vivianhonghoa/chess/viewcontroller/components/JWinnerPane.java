package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JWinnerPane extends JPanel {
    private final GameEngine gameEngine;

    public JWinnerPane(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        int winnerPlayerNumber = gameEngine.getWinnerPlayerNumber();
        JLabel jLabel = new JLabel("Player " + winnerPlayerNumber + " won !", SwingConstants.CENTER);
        jLabel.setForeground(Colors.WHITE);
        JComponentHelper.setFontSize(jLabel, 30);
        JLabelWrapper jLabelWrapper = new JLabelWrapper(jLabel, true);
        jLabelWrapper.setBackground(Colors.SECONDARY);
        JComponentHelper.setFixedSize(jLabelWrapper, 300, 60);

        this.setLayout(new GridBagLayout());
        this.add(jLabelWrapper);
    }
}
