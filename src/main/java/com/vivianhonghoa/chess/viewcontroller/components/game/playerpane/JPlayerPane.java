package com.vivianhonghoa.chess.viewcontroller.components.game.playerpane;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.ImagePath;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomLabel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JLabelWrapper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JPlayerPane extends JPanel {

    private static final int WIDTH = 200;

    private final int playerNumber;
    private final String playerColor;
    private final GameEngine gameEngine;

    public JPlayerPane(int playerNumber, String playerColor, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.playerColor = playerColor;
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setBackground(Colors.PRIMARY_DARK_2);
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.setPreferredSize(new Dimension(WIDTH, 0));
        jWrapper.setRadius(20);
        int borderSize = 15;
        jWrapper.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));

        jWrapper.add(getPlayerNameAndColorPane());
        jWrapper.add(Box.createVerticalStrut(3));
        jWrapper.add(new JRemainingTime(playerNumber, gameEngine));
        jWrapper.add(Box.createVerticalStrut(10));
        jWrapper.add(new JBackGiveUpButtons(playerNumber, gameEngine));
        jWrapper.add(Box.createVerticalStrut(10));
        jWrapper.add(new JCapturedPieces(playerNumber, gameEngine));
        jWrapper.add(Box.createVerticalStrut(10));
        jWrapper.add(new JHistory(playerNumber, gameEngine));
        jWrapper.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.add(jWrapper, BorderLayout.CENTER);
        this.setBackground(Colors.PRIMARY_DARK_1);
    }

    private JPanel getPlayerNameAndColorPane(){
        JCustomLabel jPlayerName = new JCustomLabel("Player " + playerNumber, SwingConstants.CENTER);
        jPlayerName.setShadow(4);
        jPlayerName.setForeground(Colors.SECONDARY);
        JComponentHelper.setFontSize(jPlayerName, 20);
        JComponentHelper.setFontWeightBold(jPlayerName);
        JLabelWrapper jPlayerNameWrapper = new JLabelWrapper(jPlayerName, true);
        jPlayerNameWrapper.setPaddingVertical(5);

        JLabel jPlayerColor = new JLabel("(" + playerColor + ")", SwingConstants.CENTER);
        JComponentHelper.setFontSize(jPlayerColor, 16);
        JComponentHelper.setItalic(jPlayerColor);
        jPlayerColor.setForeground(Colors.SECONDARY);
        JLabelWrapper jPlayerColorWrapper = new JLabelWrapper(jPlayerColor, true);
        jPlayerColorWrapper.setPaddingVertical(5);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setRadius(15, 15, 0, 0);
        jWrapper.setBackgroundImage(JPlayerPane.class.getResourceAsStream(ImagePath.WOOD_TEXTURE_1));
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.add(jPlayerNameWrapper);
        jWrapper.add(jPlayerColorWrapper);

        return jWrapper;
    }
}
