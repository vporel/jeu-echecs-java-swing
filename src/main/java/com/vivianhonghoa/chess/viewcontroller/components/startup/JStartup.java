package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JStartup extends JCustomPanel {
    public final static Color STARTUP_BACKGROUND_1 = new Color(30, 30, 30);
    public final static Color STARTUP_BACKGROUND_2 = Colors.PRIMARY;

    private final GameEngine gameEngine;

    public JStartup(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setLinearGradientBackground(List.of(STARTUP_BACKGROUND_1, STARTUP_BACKGROUND_2), 45);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.add(Box.createVerticalGlue());
        this.add(getAppNameAndAuthorsPane());
        this.add(Box.createVerticalStrut(20));
        this.add(new JGameSetup(gameEngine));
        this.add(Box.createVerticalStrut(10));
        this.add(new JSpecialPositions(gameEngine));
        this.add(Box.createVerticalGlue());
    }

    private JPanel getAppNameAndAuthorsPane(){

        JLabel jTitleLabel = new JLabel("Chess\u265A");
        jTitleLabel.setForeground(Colors.SECONDARY_LIGHT_1);
        jTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jTitleLabel, 50);
        JComponentHelper.setFontWeightBold(jTitleLabel);

        JLabel jAuthorsLabel = new JLabel("By Vivian and Hong Hoa");
        jAuthorsLabel.setForeground(Colors.WHITE);
        jAuthorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComponentHelper.setFontSize(jAuthorsLabel, 20);
        JComponentHelper.setItalic(jAuthorsLabel);

        JCustomPanel jWrapper = new JCustomPanel();
        jWrapper.setLayout(new BoxLayout(jWrapper, BoxLayout.Y_AXIS));
        jWrapper.add(jTitleLabel);
        jWrapper.add(Box.createVerticalStrut(10));
        jWrapper.add(jAuthorsLabel);

        return jWrapper;
    }

}
