package com.vivianhonghoa.chess;

import com.vivianhonghoa.chess.viewcontroller.JAppFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JAppFrame jAppFrame = new JAppFrame();
            jAppFrame.build();
            jAppFrame.setVisible(true);
        });
    }
}