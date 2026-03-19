package com.vivianhonghoa.chess.viewcontroller.console;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;

import javax.swing.*;
import java.awt.*;

public class JHeader extends JPanel {
    private final GameEngine gameEngine;
    private final int playerNumber;

    public JHeader(int playerNumber) {
        this.gameEngine = GameEngine.getInstance();
        this.playerNumber = playerNumber;
        build();
    }

    private void build(){
        JLabel jTitleLabel = new JLabel("");
        jTitleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        jTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(jTitleLabel, BorderLayout.CENTER);

        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameStarted(GameEngineEvent event) {
                jTitleLabel.setText(gameEngine.getPlayerContext(playerNumber).name());
                System.out.println("Game started. You are player " + playerNumber + " (" + gameEngine.getPlayerContext(playerNumber).name() + ")");
            }
        });
    }
}