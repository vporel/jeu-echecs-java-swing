package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.*;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class JStartup extends JPanel {
    public final static Color STARTUP_BACKGROUND_1 = new Color(30, 30, 30);
    public final static Color STARTUP_BACKGROUND_2 = Colors.PRIMARY;

    private final GameEngine gameEngine;

    public JStartup(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.add(Box.createVerticalGlue());
        this.add(getAppNameAndAuthorsPane());
        this.add(Box.createVerticalStrut(40));
        this.add(new JGameSetup(gameEngine));
        this.add(Box.createVerticalStrut(20));
        this.add(new JSpecialPositions(gameEngine));
        this.add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // 45° gradient: top-left → bottom-right
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float half = (float) (Math.max(getWidth(), getHeight()) / Math.sqrt(2));
        Point2D start = new Point2D.Float(cx - half / 2, cy - half / 2);
        Point2D end   = new Point2D.Float(cx + half / 2, cy + half / 2);
        g2.setPaint(new GradientPaint(start, STARTUP_BACKGROUND_1, end, STARTUP_BACKGROUND_2));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
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
