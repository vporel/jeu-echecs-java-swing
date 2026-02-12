package com.vivianhonghoa.chess;

import com.vivianhonghoa.chess.viewcontroller.AppFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame appFrame = new AppFrame();
            appFrame.build();
            appFrame.setVisible(true);
        });
    }
}