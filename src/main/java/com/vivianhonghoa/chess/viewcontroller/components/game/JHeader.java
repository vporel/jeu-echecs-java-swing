package com.vivianhonghoa.chess.viewcontroller.components.game;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomButtonWithIcon;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JLabelWrapper;
import com.vivianhonghoa.chess.viewcontroller.helpers.FontHelper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JHeader extends JPanel {

    private static final int HEIGHT = 75;

    private final GameEngine gameEngine;

    public JHeader() {
        super();
        this.gameEngine = GameEngine.getInstance();
        build();
    }


    private void build(){
        JLabel jTitleIconLabel = new JLabel("\u265A");
        JComponentHelper.setFontSize(jTitleIconLabel, 50);
        jTitleIconLabel.setForeground(Colors.SECONDARY_LIGHT_1);
        JLabelWrapper jTitleIconLabelWrapper = new JLabelWrapper(jTitleIconLabel, false);
        jTitleIconLabelWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JLabel jTitleLabel = new JLabel("Chess");
        JComponentHelper.setFontSize(jTitleLabel, 25);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(Colors.SECONDARY_LIGHT_1);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setBackground(Colors.PRIMARY_DARK_2);
        jWrapper.setRadius(20);
        int borderSize = 15;
        jWrapper.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, 0, borderSize));

        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.X_AXIS));
        jWrapper.add(Box.createHorizontalStrut(10));
        jWrapper.add(jTitleIconLabelWrapper);
        jWrapper.add(Box.createHorizontalStrut(5));
        jWrapper.add(jTitleLabel);
        jWrapper.add(Box.createHorizontalGlue());
        List<JCustomButtonWithIcon> rightButtons = getRightButtons();
        for (int i = 0; i < rightButtons.size(); i++) {
            JCustomButtonWithIcon button = rightButtons.get(i);
            jWrapper.add(button);
            if (i < rightButtons.size() - 1) {
                jWrapper.add(Box.createHorizontalStrut(5));
            }
        }
        jWrapper.add(Box.createHorizontalStrut(5));
        JComponentHelper.setMinimumWidth(jWrapper, Integer.MAX_VALUE);

        this.setLayout(new BorderLayout());
        this.add(jWrapper, BorderLayout.CENTER);
        this.setBackground(Colors.PRIMARY_DARK_1);
        JComponentHelper.setFixedHeight(this, HEIGHT);
    }

    private List<JCustomButtonWithIcon> getRightButtons(){
        JCustomButtonWithIcon jPauseResumeButton = new JCustomButtonWithIcon("PAUSE", new JLabel("\uf04c"));
        jPauseResumeButton.addActionListener(e -> {
            if (gameEngine.isPaused()) {
                gameEngine.resume();
            } else {
                gameEngine.pause();
            }
        });

        JCustomButtonWithIcon jStopButton = new JCustomButtonWithIcon("QUIT", new JLabel("\uf090"));
        jStopButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    JHeader.this,
                    "Are you sure that you want to quit the game ?",
                    "Quit Game",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (response == JOptionPane.YES_OPTION) {
                gameEngine.stop();
            }
        });

        List<JCustomButtonWithIcon> jButtons =  List.of(jPauseResumeButton, jStopButton);

        for(JCustomButtonWithIcon jButton : jButtons) {
            jButton.getIcon().setFont(FontHelper.fontAwesome());
            jButton.setSpacing(10);
            jButton.getIcon().setForeground(Colors.WHITE);
            jButton.setBackground(Colors.PRIMARY_DARK_2);
            jButton.setForeground(Colors.WHITE);
            jButton.setBorder(new RoundedBorder(Colors.SECONDARY, 2, 40));
            JComponentHelper.setFixedHeight(jButton, 40);
            JComponentHelper.changeBackgroundOnMouseHover(jButton, Colors.PRIMARY_DARK_2, Colors.PRIMARY_DARK_1);
        }

        //Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGamePaused(GameEngineEvent event) {
                jPauseResumeButton.setText("RESUME");
                ((JLabel) jPauseResumeButton.getIcon()).setText("\uf04b");
            }

            @Override
            public void onGameResumed(GameEngineEvent event) {
                jPauseResumeButton.setText("PAUSE");
                ((JLabel) jPauseResumeButton.getIcon()).setText("\uf04c");
            }
        });

        return jButtons;
    }
}
