package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.BoxesManager;

import javax.swing.*;

public class AppFrame extends JFrame {
    public static final String DEFAULT_TITLE = "Chess by Vivian and Hong Hoa";
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 800;
    public static final BoxesManager boxesManager = new BoxesManager();

    public void build() {
        setTitle(DEFAULT_TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new java.awt.GridLayout(BoxesManager.NUM_BOXES_PER_ROW, BoxesManager.NUM_BOXES_PER_COL));
        addBoxes(contentPane);
        setContentPane(contentPane);
    }

    private void addBoxes(JPanel panel){
        for(int row = 1; row <= BoxesManager.NUM_BOXES_PER_ROW; row++){
            for(int col = 1; col <= BoxesManager.NUM_BOXES_PER_COL; col++) {
                Box box = new Box(row, col, boxesManager);
                panel.add(box);
            }
        }
    }

}
