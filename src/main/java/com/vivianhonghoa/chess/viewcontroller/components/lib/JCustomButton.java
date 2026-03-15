package com.vivianhonghoa.chess.viewcontroller.components.lib;

import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class JCustomButton extends JButton {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 40;

    public JCustomButton(String text) {
        super(text);
        build();
    }

    private void build(){
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorderPainted(true);
        this.setBackground(Colors.TRANSPARENT);
        JComponentHelper.setFixedSize(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int radius = getBorder() instanceof RoundedBorder rb ? rb.getRadius() : 0;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape shape = radius > 0
                ? new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius)
                : new Rectangle(0, 0, getWidth(), getHeight());

        // Fill background clipped to the rounded shape
        g2.setClip(shape);
        g2.setColor(getBackground());
        g2.fill(shape);

        // Let Swing paint text/icon, also clipped
        super.paintComponent(g2);
        g2.dispose();
    }

    public void setWidth(int width) {
        JComponentHelper.setFixedWidth(this, width);
    }

    public void setHeight(int height) {
        JComponentHelper.setFixedHeight(this, height);
    }

    @Override
    public void setSize(int width, int height) {
        JComponentHelper.setFixedSize(this, width, height);
    }
}
