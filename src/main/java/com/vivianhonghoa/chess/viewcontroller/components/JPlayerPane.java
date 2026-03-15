package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.History;
import com.vivianhonghoa.chess.model.events.*;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(getPlayerNameAndColorPane());
        this.add(Box.createVerticalStrut(3));
        this.add(new JDivider(Colors.PRIMARY, 3));
        this.add(getRemainingTimePane());
        this.add(Box.createVerticalStrut(10));
        this.add(getBackGiveUpButtons());
        this.add(Box.createVerticalStrut(10));
        this.add(getCapturedPiecesPane());
        this.add(Box.createVerticalStrut(10));
        this.add(getHistoryPane());
        this.add(Box.createVerticalGlue());
        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(WIDTH, 0));

    }

    private JPanel getPlayerNameAndColorPane(){
        JLabel jPlayerName = new JLabel(getPlayerName(), SwingConstants.CENTER);
        JComponentHelper.setFontSize(jPlayerName, 20);
        JComponentHelper.setFontWeightBold(jPlayerName);
        JLabelWrapper jPlayerNameWrapper = new JLabelWrapper(jPlayerName, true);
        jPlayerNameWrapper.setPaddingVertical(5);
        jPlayerNameWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jPlayerColor = new JLabel("(" + playerColor + ")", SwingConstants.CENTER);
        JComponentHelper.setFontSize(jPlayerColor, 16);
        JComponentHelper.setItalic(jPlayerColor);
        jPlayerColor.setForeground(Colors.SECONDARY);
        JLabelWrapper jPlayerColorWrapper = new JLabelWrapper(jPlayerColor, true);
        jPlayerColorWrapper.setPaddingVertical(5);
        jPlayerColorWrapper.setBackground(Colors.APP_BACKGROUND);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(jPlayerNameWrapper);
        wrapper.add(jPlayerColorWrapper);
        wrapper.setBackground(Colors.APP_BACKGROUND);

        return wrapper;
    }

    private JPanel getRemainingTimePane(){
        JLabel jRemainingTimeLabel = new JLabel("Remaining time", SwingConstants.CENTER);
        JLabelWrapper jRemainingTimeLabelWrapper = new JLabelWrapper(jRemainingTimeLabel, true);
        jRemainingTimeLabelWrapper.setPaddingVertical(10);
        jRemainingTimeLabelWrapper.setBackground(Colors.APP_BACKGROUND);

        JLabel jRemainingTimeValue = new JLabel(getRemainingTimeStr(), SwingConstants.CENTER);
        JComponentHelper.setFontSize(jRemainingTimeValue, 18);
        JLabelWrapper jRemainingTimeValueWrapper = new JLabelWrapper(jRemainingTimeValue, true);
        jRemainingTimeValueWrapper.setPaddingVertical(5);
        jRemainingTimeValueWrapper.setBackground(Colors.APP_BACKGROUND);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(jRemainingTimeLabelWrapper);
        wrapper.add(jRemainingTimeValueWrapper);
        wrapper.setBackground(Colors.APP_BACKGROUND);

        // Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameTimeUpdated(GameEngineEvent event) {
                jRemainingTimeValue.setText(getRemainingTimeStr());
            }
        });

        return wrapper;
    }

    private void updateCapturedPieces(JPanel panel) {
        panel.removeAll();
        for (Piece piece : getCapturedPieces()) {
            JLabel label = new JLabel(piece.getUnicodeSymbol());
            JComponentHelper.setFontSize(label, 30);
            label.setForeground(piece.getColor() == Piece.Color.WHITE ? new Color(180, 180, 180) : Color.BLACK);
            panel.add(label);
        }
        panel.revalidate();
        panel.repaint();
    }

    private JPanel getBackGiveUpButtons(){
        JLabel jBackLabelButton = new JLabel("\u27F2");
        JComponentHelper.setFontSize(jBackLabelButton, 50);
        jBackLabelButton.setBorder(null);
        JComponentHelper.changeForegroundOnMouseHover(
                jBackLabelButton,
                Colors.BLACK,
                Colors.SECONDARY
        );
        JComponentHelper.changeCursorOnMouseHover(
                jBackLabelButton,
                Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR),
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        jBackLabelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!gameEngine.hasStarted()) return;
                gameEngine.undo(playerNumber);
            }
        });

        JLabel jGiveUpLabelButton = new JLabel("\uD83C\uDFC1");
        JComponentHelper.setFontSize(jGiveUpLabelButton, 40);
        jGiveUpLabelButton.setBorder(null);
        JComponentHelper.changeForegroundOnMouseHover(
                jGiveUpLabelButton,
                Colors.BLACK,
                Colors.SECONDARY
        );
        JComponentHelper.changeCursorOnMouseHover(
                jGiveUpLabelButton,
                Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR),
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        jGiveUpLabelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!gameEngine.hasStarted()) return;
                int response = JOptionPane.showConfirmDialog(
                        JPlayerPane.this,
                        "Are you sure that you want to give up ?",
                        "Give up",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    gameEngine.giveUp(playerNumber);
                }
            }
        });

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.add(Box.createHorizontalGlue());
        wrapper.add(jBackLabelButton);
        wrapper.add(Box.createHorizontalStrut(20));
        wrapper.add(jGiveUpLabelButton);
        wrapper.add(Box.createHorizontalGlue());
        wrapper.setBackground(Colors.APP_BACKGROUND);
        JComponentHelper.setFixedHeight(wrapper, 50);
        return wrapper;
    }

    private JPanel getHistoryPane(){
        JLabel jHistoryLabel = new JLabel("History");
        JComponentHelper.setFontWeightBold(jHistoryLabel);
        JLabelWrapper jHistoryLabelWrapper = new JLabelWrapper(jHistoryLabel, true);
        jHistoryLabelWrapper.setPaddingVertical(10);

        JPanel jHistoryContentPanel = new JPanel();
        jHistoryContentPanel.setBackground(Colors.APP_BACKGROUND);
        JComponentHelper.setMinimumHeight(jHistoryContentPanel, 100);
        jHistoryContentPanel.setLayout(new BoxLayout(jHistoryContentPanel, BoxLayout.Y_AXIS));

        //History events
        History history = gameEngine.getPlayerHistory(playerNumber);
        history.addObserver(new HistoryObserver() {
            @Override
            public void onChange(HistoryEvent event) {
                jHistoryContentPanel.removeAll();
                List<String> moves = history.getFormattedList();
                for(int i = 0; i < moves.size(); i++){
                    String move = (i + 1) + ". " + moves.get(i);
                    JLabel jMoveLabel = new JLabel(move);
                    jMoveLabel.setForeground(Colors.SECONDARY);

                    JLabelWrapper jMoveLabelWrapper = new JLabelWrapper(jMoveLabel, true);
                    jHistoryContentPanel.add(jMoveLabelWrapper);
                }
            }
        });

        JPanel jHistoryPanel = new JPanel();
        jHistoryPanel.setBackground(Colors.APP_BACKGROUND);
        jHistoryPanel.setLayout(new BoxLayout(jHistoryPanel, BoxLayout.Y_AXIS));
        jHistoryPanel.add(jHistoryLabelWrapper);
        jHistoryPanel.add(new JDivider());
        jHistoryPanel.add(Box.createVerticalStrut(5));
        jHistoryPanel.add(jHistoryContentPanel);
        return jHistoryPanel;
    }

    private JPanel getCapturedPiecesPane() {
        JLabel jCapturedLabel = new JLabel("Captured pieces", SwingConstants.CENTER);
        JComponentHelper.setFontWeightBold(jCapturedLabel);
        JLabelWrapper jCapturedLabelWrapper = new JLabelWrapper(jCapturedLabel, true);
        jCapturedLabelWrapper.setPaddingVertical(10);

        JPanel capturedPiecesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        capturedPiecesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        capturedPiecesPanel.setBackground(Colors.APP_BACKGROUND);
        JComponentHelper.setFixedHeight(capturedPiecesPanel, 50);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(jCapturedLabelWrapper);
        wrapper.add(new JDivider());
        wrapper.add(capturedPiecesPanel);
        wrapper.setBackground(Colors.APP_BACKGROUND);

        // Board events - update captured pieces display
        gameEngine.getBoard().addObserver(new BoardObserver() {
            @Override
            public void onPieceCaptured(BoardEvent event) {
                updateCapturedPieces(capturedPiecesPanel);
            }
        });

        return wrapper;
    }
}
