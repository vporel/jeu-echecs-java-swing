package com.vivianhonghoa.chess.viewcontroller.components.game;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.model.pieces.*;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.game.playerpane.JPlayerPane;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.helpers.JBorderLayoutHelper;

import javax.swing.*;
import java.awt.*;

public class JGamePane extends JPanel {
    private final GameEngine gameEngine;

    public JGamePane(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setLayout(new BorderLayout());
        this.setBackground(Colors.PRIMARY_DARK_1);

        JCustomPanel jSquaresContainerWrapper = new JCustomPanel();
        jSquaresContainerWrapper.setLayout(new GridBagLayout());
        jSquaresContainerWrapper.add(new JSquaresContainer(gameEngine));
        jSquaresContainerWrapper.setBackground(Colors.PRIMARY_DARK_1);


        this.add(new JHeader(gameEngine), BorderLayout.NORTH);
        this.add(new JFooter(gameEngine), BorderLayout.SOUTH);
        this.add(jSquaresContainerWrapper, BorderLayout.CENTER);
        this.add(new JPlayerPane(1, "White", gameEngine), BorderLayout.WEST);
        this.add(new JPlayerPane(2, "Black", gameEngine), BorderLayout.EAST);
        this.setBackground(Colors.APP_BACKGROUND);

        gameEngine.getBoard().setPromotionHandler(color -> {
            String[] options = {"\u265B Queen", "\u265C Rook", "\u265D Bishop", "\u265E Knight"};
            int choice = JOptionPane.showOptionDialog(
                    this, "Choose promotion piece:", "Pawn Promotion",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            return switch (choice) {
                case 1 -> new Rook(color);
                case 2 -> new Bishop(color);
                case 3 -> new Knight(color);
                default -> new Queen(color);
            };
        });

        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameEnded(GameEngineEvent event) {
                JWinnerPane jWinnerPane = new JWinnerPane(gameEngine, () -> {
                    JBorderLayoutHelper.changeComponent(JGamePane.this, BorderLayout.CENTER, jSquaresContainerWrapper);
                });
                JBorderLayoutHelper.changeComponent(JGamePane.this, BorderLayout.CENTER, jWinnerPane);
            }
        });
    }

}
