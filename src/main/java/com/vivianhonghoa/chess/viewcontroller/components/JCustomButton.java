package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.viewcontroller.Colors;

import javax.swing.*;
import java.awt.*;

public class JCustomButton extends JButton {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 40;

    public JCustomButton(String text) {
        super(text);
        build();
    }

    private void build(){
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setOpaque(true);
        this.setBackground(Colors.WHITE);
    }
}
