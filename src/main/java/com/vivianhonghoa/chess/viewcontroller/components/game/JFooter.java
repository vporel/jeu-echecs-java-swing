package com.vivianhonghoa.chess.viewcontroller.components.game;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.PlayerContext;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;

public class JFooter extends JCustomPanel {

    private static final int HEIGHT = 60;

    private final GameEngine gameEngine;

    public JFooter() {
        super();
        this.gameEngine = GameEngine.getInstance();
        build();
    }

    public String getPlayerName(int playerNumber){
        return gameEngine.getPlayerContext(playerNumber).name();
    }

    private String getTurnText(int playerNumber) {
        PlayerContext playerContext = gameEngine.getPlayerContext(playerNumber);
        String name = playerContext.name();
        String color = playerContext.player().getColor() == Piece.Color.WHITE ? "White" : "Black";
        return name + " (" + color + ")";
    }

    private void build(){
        JLabel jCentralText = new JLabel(getTurnText(gameEngine.getCurrentPlayerNumber()), SwingConstants.CENTER);
        JComponentHelper.setFontSize(jCentralText, 19);
        jCentralText.setForeground(Colors.WHITE);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setBackground(Colors.PRIMARY_DARK_2);
        jWrapper.setRadius(20);
        int borderRadius = 15;
        jWrapper.setBorder(BorderFactory.createEmptyBorder(5, borderRadius, borderRadius, borderRadius));

        jWrapper.add(jCentralText);

        this.add(jWrapper);
        this.setBackground(Colors.PRIMARY_DARK_1);
        JComponentHelper.setFixedHeight(this, HEIGHT);

        //Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onPlayerTurnChanged(GameEngineEvent event) {
                jCentralText.setText(getTurnText(gameEngine.getCurrentPlayerNumber()));
            }

            @Override
            public void onGameEnded(GameEngineEvent event) {
                int winner = gameEngine.getWinnerPlayerNumber();
                jCentralText.setText(winner == 0 ? "Game Over. Draw ! (Stalemate)" : "Game Over. Winner : " + getPlayerName(winner));
            }
        });
    }
}
