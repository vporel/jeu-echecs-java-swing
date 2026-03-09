package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.components.JGamePane;
import com.vivianhonghoa.chess.viewcontroller.components.JStartupPane;

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
        JStartupPane jStartupPane = new JStartupPane(gameEngine);
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameStarted(GameEngineEvent event) {
                JGamePane jGamePane = new JGamePane(gameEngine);
                setContentPane(jGamePane);
            }
        });
        setContentPane(jStartupPane);
    }

}
