package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;

import javax.swing.*;

public class JFooter extends JPanel {

    private static final int HEIGHT = 40;

    private final GameEngine gameEngine;

    public JFooter(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private String getPlayerName(int playerNumber) {
        return "Player " + playerNumber;
    }

    private void build(){
        JLabel currentPlayerLabel = new JLabel("Turn : " + getPlayerName(gameEngine.getCurrentPlayerNumber()), SwingConstants.CENTER);


        this.add(currentPlayerLabel);
        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(0, HEIGHT));
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.SQUARES_CONTAINER_BACKGROUND));

        //Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onPlayerTurnChanged(GameEngineEvent event) {
                currentPlayerLabel.setText("Turn : " + getPlayerName(gameEngine.getCurrentPlayerNumber()));
            }
        });
    }
}
