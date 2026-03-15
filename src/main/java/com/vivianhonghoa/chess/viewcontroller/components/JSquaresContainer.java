package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.model.Board;
import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;

import javax.swing.*;
import java.awt.*;

public class JSquaresContainer extends JPanel {

    private static final int PREFFERED_SIZE = 600;
    private static final int EDGE_BANDS_SIZE = 40;

    private final GameEngine gameEngine;

    public JSquaresContainer(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setBackground(Colors.SQUARES_CONTAINER_BACKGROUND);
        this.setLayout(new BorderLayout());
        this.add(getLeftPanel(), BorderLayout.WEST);
        this.add(getTopPanel(), BorderLayout.NORTH);
        this.add(getRightPanel(), BorderLayout.EAST);
        this.add(getBottomPanel(), BorderLayout.SOUTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(PREFFERED_SIZE, PREFFERED_SIZE));
    }

    private JPanel getCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(Board.SIZE, Board.SIZE));
        for(int row = 0; row < Board.SIZE; row++){
            for(int col = 0; col < Board.SIZE; col++) {
                JSquare jSquare = new JSquare(row, col, gameEngine);
                centerPanel.add(jSquare);
            }
        }
        return centerPanel;
    }

    private JPanel getTopPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, EDGE_BANDS_SIZE));
        topPanel.setLayout(new BorderLayout());
        // Add fixed-size left corner
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        topPanel.add(emptyLabel, BorderLayout.WEST);
        // Center letters in a GridLayout
        JPanel topCenter = new JPanel(new GridLayout(1, Board.SIZE));
        for(char c = 'A'; c < 'A' + Board.SIZE; c++){
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            topCenter.add(label);
        }
        topPanel.add(topCenter, BorderLayout.CENTER);
        // Add fixed-size right corner
        JLabel emptyLabel2 = new JLabel("");
        emptyLabel2.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        topPanel.add(emptyLabel2, BorderLayout.EAST);
        return topPanel;
    }

    private JPanel getBottomPanel(){
        //Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(0, EDGE_BANDS_SIZE));
        bottomPanel.setLayout(new BorderLayout());
        // Add fixed-size left corner
        JLabel emptyLabel3 = new JLabel("");
        emptyLabel3.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        bottomPanel.add(emptyLabel3, BorderLayout.WEST);
        // Center letters in a GridLayout
        JPanel bottomCenter = new JPanel(new GridLayout(1, Board.SIZE));
        for(char c = 'A'; c < 'A' + Board.SIZE; c++){
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            bottomCenter.add(label);
        }
        bottomPanel.add(bottomCenter, BorderLayout.CENTER);
        // Add fixed-size right corner
        JLabel emptyLabel4 = new JLabel("");
        emptyLabel4.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        bottomPanel.add(emptyLabel4, BorderLayout.EAST);
        return bottomPanel;
    }

    private JPanel getLeftPanel(){
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        //Add the numbers
        leftPanel.setLayout(new GridLayout(Board.SIZE, 1));
        for(int i = 1; i <= Board.SIZE; i++){
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            leftPanel.add(label);
        }

        return leftPanel;
    }

    private JPanel getRightPanel(){
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        //Add the numbers
        rightPanel.setLayout(new GridLayout(Board.SIZE, 1));
        for(int i = 1; i <= Board.SIZE; i++){
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            rightPanel.add(label);
        }

        return rightPanel;
    }
}
