package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
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


    private void build(){

        this.setBackground(Colors.APP_BACKGROUND);
        this.setPreferredSize(new java.awt.Dimension(0, HEIGHT));
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.SQUARES_CONTAINER_BACKGROUND));
    }
}
