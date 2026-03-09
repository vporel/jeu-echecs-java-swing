package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.GameEngine;
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
        JCustomButton pauseButton = new JCustomButton("PAUSE");
        JCustomButton stopButton = new JCustomButton("ARRETER");

        this.add(pauseButton);
        this.add(stopButton);
        this.setBackground(Colors.PRIMARY);
        this.setPreferredSize(new java.awt.Dimension(0, HEIGHT));
    }
}
