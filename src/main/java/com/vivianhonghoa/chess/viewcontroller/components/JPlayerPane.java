package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.BoardEvent;
import com.vivianhonghoa.chess.model.events.BoardObserver;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    private String getPlayerName(){
        return "Player " + playerNumber;
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

    private List<Piece> getCapturedPieces() {
        if (playerNumber == 1) {
            return gameEngine.getBoard().getCapturedByWhite();
        } else {
            return gameEngine.getBoard().getCapturedByBlack();
        }
    }

    private void build(){
        JLabel jPlayerName = new JLabel(getPlayerName(), SwingConstants.CENTER);
        JComponentHelper.setBold(jPlayerName);
        JComponentHelper.setFontSize(jPlayerName, 20);
        JLabelWrapper jPlayerNameWrapper = new JLabelWrapper(jPlayerName, true);
        jPlayerNameWrapper.setPaddingVertical(10);
        jPlayerNameWrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Colors.PRIMARY));
        jPlayerNameWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jPlayerColor = new JLabel("(" + playerColor + ")", SwingConstants.CENTER);
        JComponentHelper.setFontSize(jPlayerColor, 16);
        jPlayerColor.setForeground(Colors.SECONDARY);
        JLabelWrapper jPlayerColorWrapper = new JLabelWrapper(jPlayerColor, true);
        jPlayerColorWrapper.setPaddingVertical(5);
        jPlayerColorWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jRemainingTimeLabel = new JLabel("Remaining time", SwingConstants.CENTER);
        JLabelWrapper jRemainingTimeLabelWrapper = new JLabelWrapper(jRemainingTimeLabel, true);
        jRemainingTimeLabelWrapper.setPaddingVertical(10);
        jRemainingTimeLabelWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jRemainingTimeValue = new JLabel(getRemainingTimeStr(), SwingConstants.CENTER);
        JComponentHelper.setFontSize(jRemainingTimeValue, 18);
        JLabelWrapper jRemainingTimeValueWrapper = new JLabelWrapper(jRemainingTimeValue, true);
        jRemainingTimeValueWrapper.setPaddingVertical(5);
        jRemainingTimeValueWrapper.setBackground(Colors.APP_BACKGROUND);

        // Captured pieces section
        JLabel jCapturedLabel = new JLabel("Pièces capturées", SwingConstants.CENTER);
        JComponentHelper.setBold(jCapturedLabel);
        JLabelWrapper jCapturedLabelWrapper = new JLabelWrapper(jCapturedLabel, true);
        jCapturedLabelWrapper.setPaddingVertical(10);
        jCapturedLabelWrapper.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Colors.PRIMARY));
        jCapturedLabelWrapper.setBackground(Colors.APP_BACKGROUND);

        JPanel capturedPiecesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        capturedPiecesPanel.setBackground(Colors.APP_BACKGROUND);
        capturedPiecesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jPlayerNameWrapper);
        this.add(jPlayerColorWrapper);
        this.add(jRemainingTimeLabelWrapper);
        this.add(jRemainingTimeValueWrapper);
        this.add(Box.createVerticalStrut(10));
        this.add(getBackForgiveButtons());
        this.add(Box.createVerticalGlue());
        this.add(jCapturedLabelWrapper);
        this.add(capturedPiecesPanel);
        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(WIDTH, 0));

        // Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameTimeUpdated(GameEngineEvent event) {
                jRemainingTimeValue.setText(getRemainingTimeStr());
            }
        });

        // Board events - update captured pieces display
        gameEngine.getBoard().addObserver(new BoardObserver() {
            @Override
            public void onPieceCaptured(BoardEvent event) {
                updateCapturedPieces(capturedPiecesPanel);
            }
        });
    }

    private void updateCapturedPieces(JPanel panel) {
        panel.removeAll();
        for (Piece piece : getCapturedPieces()) {
            JLabel label = new JLabel(piece.getUnicodeSymbol());
            label.setFont(new Font("Serif", Font.PLAIN, 28));
            // Use contrasting colors so pieces are visible on the white background
            label.setForeground(piece.getColor() == Piece.Color.BLANC ? Colors.PRIMARY_LIGHT_1 : Color.BLACK);
            panel.add(label);
        }
        panel.revalidate();
        panel.repaint();
    }

    private JPanel getBackForgiveButtons(){
        JLabel jBackLabelButton = new JLabel("\u27F2");
        JComponentHelper.setFontSize(jBackLabelButton, 50);
        jBackLabelButton.setBorder(null);

        JLabel jForgiveLabelButton = new JLabel("\uD83C\uDFC1");
        JComponentHelper.setFontSize(jForgiveLabelButton, 40);
        jForgiveLabelButton.setBorder(null);

        JPanel jBackForgiveButtonsWrapper = new JPanel();
        jBackForgiveButtonsWrapper.setLayout(new BoxLayout(jBackForgiveButtonsWrapper, BoxLayout.X_AXIS));
        jBackForgiveButtonsWrapper.add(Box.createHorizontalGlue());
        jBackForgiveButtonsWrapper.add(jBackLabelButton);
        jBackForgiveButtonsWrapper.add(Box.createHorizontalStrut(20));
        jBackForgiveButtonsWrapper.add(jForgiveLabelButton);
        jBackForgiveButtonsWrapper.add(Box.createHorizontalGlue());
        jBackForgiveButtonsWrapper.setBackground(Colors.APP_BACKGROUND);

        return jBackForgiveButtonsWrapper;
    }
}
