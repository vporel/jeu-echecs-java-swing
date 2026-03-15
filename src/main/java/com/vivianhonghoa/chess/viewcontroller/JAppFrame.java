package com.vivianhonghoa.chess.viewcontroller;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.components.JGamePane;
import com.vivianhonghoa.chess.viewcontroller.components.startup.JStartup;

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
        JStartup jStartup = new JStartup(gameEngine);
        setContentPane(jStartup);

        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameStarted(GameEngineEvent event) {
                changeContentPane(new JGamePane(gameEngine));
            }

            @Override
            public void onGameStopped(GameEngineEvent event) {
                changeContentPane(jStartup);
            }
        });
    }

    private void changeContentPane(JPanel newPane) {
        setContentPane(newPane);
        // Ensure the frame updates its layout and repaints after changing the content pane
        SwingUtilities.invokeLater(() -> {
            // revalidate will re-run layout; repaint will refresh display
            JAppFrame.this.revalidate();
            JAppFrame.this.repaint();
        });
    }

}
