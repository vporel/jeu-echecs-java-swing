package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class JCustomPanel extends JPanel {

    private int radius = 0;

    public JCustomPanel() {
        super();
        this.setOpaque(false);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        setOpaque(false);
        repaint();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.setOpaque(true);
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
        if(border instanceof RoundedBorder roundedBorder) {
            setRadius(roundedBorder.getRadius());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (radius > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        } else {
            super.paintComponent(g);
        }
    }
}
