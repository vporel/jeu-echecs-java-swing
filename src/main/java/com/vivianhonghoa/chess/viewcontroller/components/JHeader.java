package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;

import javax.swing.*;

public class JHeader extends JPanel {

    private static final int HEIGHT = 60;

    private final GameEngine gameEngine;

    public JHeader(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }


    private void build(){
        JCustomButton pauseResumeButton = new JCustomButton("PAUSE");
        pauseResumeButton.addActionListener(e -> {
            if (gameEngine.isPaused()) {
                gameEngine.resume();
            } else {
                gameEngine.pause();
            }
        });

        JCustomButton stopButton = new JCustomButton("ARRETER");
        stopButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    JHeader.this,
                    "Êtes-vous sûr de vouloir arrêter la partie ?",
                    "Confirmer l'arrêt",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (response == JOptionPane.YES_OPTION) {
                gameEngine.stop();
            }
        });

        this.add(pauseResumeButton);
        this.add(stopButton);
        this.setBackground(Colors.PRIMARY);
        this.setPreferredSize(new java.awt.Dimension(0, HEIGHT));

        //Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGamePaused(GameEngineEvent event) {
                pauseResumeButton.setText("REPRENDRE");
            }

            @Override
            public void onGameResumed(GameEngineEvent event) {
                pauseResumeButton.setText("PAUSE");
            }
        });
    }
}
