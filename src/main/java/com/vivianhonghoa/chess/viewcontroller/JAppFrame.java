package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.GameEngine;

import javax.swing.*;
import java.awt.*;

public class JAppFrame extends JFrame {
    public static final String DEFAULT_TITLE = "Chess by Vivian and Hong Hoa";
    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 800;
    public static final GameEngine gameEngine = new GameEngine();

    public void build() {
        setTitle(DEFAULT_TITLE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        JPanel jSquaresContainerWrapper = new JPanel();
        jSquaresContainerWrapper.setLayout(new GridBagLayout());
        jSquaresContainerWrapper.add(new JSquaresContainer(gameEngine));

        contentPane.add(new JHeader(gameEngine), BorderLayout.NORTH);
        contentPane.add(new JFooter(gameEngine), BorderLayout.SOUTH);
        contentPane.add(jSquaresContainerWrapper, BorderLayout.CENTER);
        contentPane.add(new JPlayerPanel(1, gameEngine), BorderLayout.WEST);
        contentPane.add(new JPlayerPanel(2, gameEngine), BorderLayout.EAST);
        contentPane.setBackground(Colors.APP_BACKGROUND);
        setContentPane(contentPane);
    }

}
