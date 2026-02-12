package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.BoxData;
import com.vivianhonghoa.chess.model.BoxesManager;
import com.vivianhonghoa.chess.model.BoxDataEvent;
import com.vivianhonghoa.chess.model.BoxesManagerListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Box extends JPanel {
    private static final int SIZE = 5; // Size of the box
    private final int row;
    private final int col;
    private final BoxesManager boxesManager;

    public Box(int row, int col, BoxesManager boxesManager) {
        this.row = row;
        this.col = col;
        this.boxesManager = boxesManager;
        build();
    }

    private void build(){
        this.setMaximumSize(new Dimension(SIZE, SIZE));
        setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(border);
        handleClick();
        //Listen the manager
        boxesManager.addListener(new BoxesManagerListener() {
            @Override
            public void boxStateUpdated(BoxDataEvent event) {
                BoxData boxData = (BoxData) event.getSource();
                if(!boxData.checkPosition(row, col)) return;
                switch(boxData.getState()){
                    case CLICKED: setBackground(Color.RED); break;
                    case HOVERED: setBackground(Color.GREEN); break;
                    default: setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
                }
            }
        });
    }

    private void handleClick(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boxesManager.boxClicked(row, col);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                boxesManager.boxHovered(row, col);
            }
        });
    }
}
