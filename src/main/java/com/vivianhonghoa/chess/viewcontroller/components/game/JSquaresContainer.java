package com.vivianhonghoa.chess.viewcontroller.components.game;

import com.vivianhonghoa.chess.model.engine.Board;
import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.ImagePath;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;

import javax.swing.*;
import java.awt.*;

public class JSquaresContainer extends JCustomPanel {

    private static final int PREFFERED_SIZE = 600;
    private static final int EDGE_BANDS_SIZE = 40;

    private final GameEngine gameEngine;

    public JSquaresContainer() {
        super();
        this.gameEngine = GameEngine.getInstance();
        build();
    }

    private void build(){
        this.setShadow(10);
        this.setBackgroundImage(JSquaresContainer.class.getResourceAsStream(ImagePath.WOOD_TEXTURE_1));
        this.setLayout(new BorderLayout());
        this.setRadius(20);
        this.setPreferredSize(new Dimension(PREFFERED_SIZE, PREFFERED_SIZE));
        int borderSize = 20;
        this.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));

        this.add(getLeftPanel(), BorderLayout.WEST);
        this.add(getTopPanel(), BorderLayout.NORTH);
        this.add(getRightPanel(), BorderLayout.EAST);
        this.add(getBottomPanel(), BorderLayout.SOUTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
    }

    private JPanel getCenterPanel(){
        JCustomPanel jCenterPanel = new JCustomPanel();
        jCenterPanel.setBorder(new RoundedBorder(Colors.SECONDARY, 3, 0));
        jCenterPanel.setLayout(new GridLayout(Board.SIZE, Board.SIZE));
        for(int row = 0; row < Board.SIZE; row++){
            for(int col = 0; col < Board.SIZE; col++) {
                JSquare jSquare = new JSquare(row, col, gameEngine);
                jCenterPanel.add(jSquare);
            }
        }
        return jCenterPanel;
    }

    private JPanel getTopPanel(){
        JCustomPanel topPanel = new JCustomPanel();
        topPanel.setPreferredSize(new Dimension(0, EDGE_BANDS_SIZE));
        topPanel.setLayout(new BorderLayout());
        // Add fixed-size left corner
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        topPanel.add(emptyLabel, BorderLayout.WEST);
        // Center letters in a GridLayout
        JCustomPanel topCenter = new JCustomPanel(new GridLayout(1, Board.SIZE));
        for(char c = 'A'; c < 'A' + Board.SIZE; c++){
            JLabel jLabel = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            jLabel.setFont(new Font("Serif", Font.PLAIN, 17));
            jLabel.setForeground(Colors.SECONDARY_LIGHT_1);
            topCenter.add(jLabel);
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
        JCustomPanel bottomPanel = new JCustomPanel();
        bottomPanel.setPreferredSize(new Dimension(0, EDGE_BANDS_SIZE));
        bottomPanel.setLayout(new BorderLayout());
        // Add fixed-size left corner
        JLabel emptyLabel3 = new JLabel("");
        emptyLabel3.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        bottomPanel.add(emptyLabel3, BorderLayout.WEST);
        // Center letters in a GridLayout
        JCustomPanel bottomCenter = new JCustomPanel(new GridLayout(1, Board.SIZE));
        for(char c = 'A'; c < 'A' + Board.SIZE; c++){
            JLabel jLabel = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            jLabel.setFont(new Font("Serif", Font.PLAIN, 17));
            jLabel.setForeground(Colors.SECONDARY_LIGHT_1);
            bottomCenter.add(jLabel);
        }
        bottomPanel.add(bottomCenter, BorderLayout.CENTER);
        // Add fixed-size right corner
        JLabel emptyLabel4 = new JLabel("");
        emptyLabel4.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        bottomPanel.add(emptyLabel4, BorderLayout.EAST);
        return bottomPanel;
    }

    private JPanel getLeftPanel(){
        JCustomPanel leftPanel = new JCustomPanel();
        leftPanel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        //Add the numbers
        leftPanel.setLayout(new GridLayout(Board.SIZE, 1));
        for(int i = 1; i <= Board.SIZE; i++){
            JLabel jLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            jLabel.setFont(new Font("Serif", Font.PLAIN, 17));
            jLabel.setForeground(Colors.SECONDARY_LIGHT_1);
            leftPanel.add(jLabel);
        }

        return leftPanel;
    }

    private JPanel getRightPanel(){
        JCustomPanel rightPanel = new JCustomPanel();
        rightPanel.setPreferredSize(new Dimension(EDGE_BANDS_SIZE, 0));
        //Add the numbers
        rightPanel.setLayout(new GridLayout(Board.SIZE, 1));
        for(int i = 1; i <= Board.SIZE; i++){
            JLabel jLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            jLabel.setFont(new Font("Serif", Font.PLAIN, 17));
            jLabel.setForeground(Colors.SECONDARY_LIGHT_1);
            rightPanel.add(jLabel);
        }

        return rightPanel;
    }
}
