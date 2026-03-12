package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.pieces.*;
import com.vivianhonghoa.chess.viewcontroller.Colors;

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

        JPanel jSquaresContainerWrapper = new JPanel();
        jSquaresContainerWrapper.setLayout(new GridBagLayout());
        jSquaresContainerWrapper.add(new JSquaresContainer(gameEngine));

        this.add(new JHeader(gameEngine), BorderLayout.NORTH);
        this.add(new JFooter(gameEngine), BorderLayout.SOUTH);
        this.add(jSquaresContainerWrapper, BorderLayout.CENTER);
        this.add(new JPlayerPane(1, "White", gameEngine), BorderLayout.WEST);
        this.add(new JPlayerPane(2, "Black", gameEngine), BorderLayout.EAST);
        this.setBackground(Colors.APP_BACKGROUND);
    }
}
