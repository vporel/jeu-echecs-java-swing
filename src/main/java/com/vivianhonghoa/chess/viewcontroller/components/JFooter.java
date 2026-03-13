package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

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
        JLabel jCurrentPlayerLabel = new JLabel("Turn : " + getPlayerName(gameEngine.getCurrentPlayerNumber()), SwingConstants.CENTER);
        JComponentHelper.setFontSize(jCurrentPlayerLabel, 19);

        this.add(jCurrentPlayerLabel);
        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(0, HEIGHT));
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.SQUARES_CONTAINER_BACKGROUND));

        //Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onPlayerTurnChanged(GameEngineEvent event) {
                jCurrentPlayerLabel.setText("Turn : " + getPlayerName(gameEngine.getCurrentPlayerNumber()));
            }

            @Override
            public void onGameEnded(GameEngineEvent event) {
                int winner = gameEngine.getWinnerPlayerNumber();
                jCurrentPlayerLabel.setText(winner == 0 ? "Game Over. Draw ! (Stalemate)" : "Game Over. Winner : " + getPlayerName(winner));
            }
        });
    }
}
