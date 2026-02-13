package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.Board;
import com.vivianhonghoa.chess.model.GameEngine;

import javax.swing.*;

public class AppFrame extends JFrame {
    public static final String DEFAULT_TITLE = "Chess by Vivian and Hong Hoa";
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 800;
    public static final GameEngine gameEngine = new GameEngine();

    public void build() {
        setTitle(DEFAULT_TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new java.awt.GridLayout(Board.TAILLE, Board.TAILLE));
        addBoxes(contentPane);
        setContentPane(contentPane);
    }

    private void addBoxes(JPanel panel){
        for(int row = 0; row < Board.TAILLE; row++){
            for(int col = 0; col < Board.TAILLE; col++) {
                Box box = new Box(row, col, gameEngine);
                panel.add(box);
            }
        }
    }

}
