package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JHeader extends JPanel {

    private static final int HEIGHT = 60;

    private final GameEngine gameEngine;

    public JHeader(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }


    private void build(){
        JLabel jTitleLabel = new JLabel("Chess");
        JComponentHelper.setFontSize(jTitleLabel, 25);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(Colors.SECONDARY_LIGHT_1);

        JCustomButton jPauseResumeButton = new JCustomButton("PAUSE");
        JComponentHelper.setFixedHeight(jPauseResumeButton, 40);
        jPauseResumeButton.addActionListener(e -> {
            if (gameEngine.isPaused()) {
                gameEngine.resume();
            } else {
                gameEngine.pause();
            }
        });

        JCustomButton jStopButton = new JCustomButton("QUIT");
        JComponentHelper.setFixedHeight(jStopButton, 40);
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

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalStrut(10));
        this.add(jTitleLabel);
        this.add(Box.createHorizontalGlue());
        this.add(jPauseResumeButton);
        this.add(Box.createHorizontalStrut(5));
        this.add(jStopButton);
        this.add(Box.createHorizontalStrut(5));
        this.setBackground(Colors.PRIMARY);
        this.setPreferredSize(new Dimension(0, HEIGHT));

        //Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGamePaused(GameEngineEvent event) {
                jPauseResumeButton.setText("RESUME");
            }

            @Override
            public void onGameResumed(GameEngineEvent event) {
                jPauseResumeButton.setText("PAUSE");
            }
        });
    }

}
