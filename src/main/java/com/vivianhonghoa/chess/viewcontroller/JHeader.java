package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.GameEngine;

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

        this.setBackground(Colors.PRIMARY);
        this.setPreferredSize(new java.awt.Dimension(0, HEIGHT));
    }
}
