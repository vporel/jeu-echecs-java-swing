package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JWinnerPane extends JPanel {
    private final GameEngine gameEngine;
    private final Runnable onBack;

    public JWinnerPane(GameEngine gameEngine, Runnable onBack) {
        super();
        this.gameEngine = gameEngine;
        this.onBack = onBack;
        build();
    }

    private void build(){
        int winnerPlayerNumber = gameEngine.getWinnerPlayerNumber();

        String message = winnerPlayerNumber == 0 ? "Draw ! (Stalemate)" : "Player " + winnerPlayerNumber + " won !";
        JLabel jLabel = new JLabel(message, SwingConstants.CENTER);
        jLabel.setForeground(Colors.WHITE);
        JComponentHelper.setFontSize(jLabel, 30);
        JLabelWrapper jLabelWrapper = new JLabelWrapper(jLabel, true);
        jLabelWrapper.setBackground(Colors.SECONDARY);
        jLabelWrapper.setRadius(15);
        JComponentHelper.setFixedSize(jLabelWrapper, 300, 60);

        JCustomButton jBackButton = new JCustomButton("\u2190 Back to board");
        jBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jBackButton.setWidth(150);
        jBackButton.addActionListener(e -> {
            onBack.run();
        });

        JPanel jContentPane = new JPanel();
        jContentPane.setLayout(new BoxLayout(jContentPane, BoxLayout.Y_AXIS));
        jContentPane.add(jLabelWrapper);
        jContentPane.add(Box.createVerticalStrut(20));
        jContentPane.add(jBackButton);

        this.setLayout(new GridBagLayout());
        this.add(jContentPane);
    }
}
