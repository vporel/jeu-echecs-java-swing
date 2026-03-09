package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.Board;
import com.vivianhonghoa.chess.model.GameEngine;

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
        //Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        //Add the numbers
        leftPanel.setLayout(new GridLayout(Board.TAILLE, 1));
        for(int i = Board.TAILLE; i > 0; i--){
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            leftPanel.add(label);
        }

        //Top panel
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, EDGE_BANDS_SIZE));
        topPanel.setLayout(new BorderLayout());
        // Add fixed-size left corner
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        topPanel.add(emptyLabel, BorderLayout.WEST);
        // Center letters in a GridLayout
        JPanel topCenter = new JPanel(new GridLayout(1, Board.TAILLE));
        for(char c = 'A'; c < 'A' + Board.TAILLE; c++){
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            topCenter.add(label);
        }
        topPanel.add(topCenter, BorderLayout.CENTER);
        // Add fixed-size right corner
        JLabel emptyLabel2 = new JLabel("");
        emptyLabel2.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        topPanel.add(emptyLabel2, BorderLayout.EAST);

        //Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        rightPanel.setLayout(new GridLayout(Board.TAILLE, 1));
        //Add the numbers
        for(int i = Board.TAILLE; i > 0; i--){
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            rightPanel.add(label);
        }

        //Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(0, EDGE_BANDS_SIZE));
        bottomPanel.setLayout(new BorderLayout());
        // Add fixed-size left corner
        JLabel emptyLabel3 = new JLabel("");
        emptyLabel3.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        bottomPanel.add(emptyLabel3, BorderLayout.WEST);
        // Center letters in a GridLayout
        JPanel bottomCenter = new JPanel(new GridLayout(1, Board.TAILLE));
        for(char c = 'A'; c < 'A' + Board.TAILLE; c++){
            JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            label.setFont(new Font("Serif", Font.PLAIN, 17));
            bottomCenter.add(label);
        }
        bottomPanel.add(bottomCenter, BorderLayout.CENTER);
        // Add fixed-size right corner
        JLabel emptyLabel4 = new JLabel("");
        emptyLabel4.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        bottomPanel.add(emptyLabel4, BorderLayout.EAST);

        //Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(Board.TAILLE, Board.TAILLE));
        for(int row = 0; row < Board.TAILLE; row++){
            for(int col = 0; col < Board.TAILLE; col++) {
                JSquare jSquare = new JSquare(row, col, gameEngine);
                centerPanel.add(jSquare);
            }
        }

        this.setBackground(Colors.SQUARES_CONTAINER_BACKGROUND);
        this.setLayout(new BorderLayout());
        this.add(leftPanel, BorderLayout.WEST);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(PREFFERED_SIZE, PREFFERED_SIZE));
    }
}
