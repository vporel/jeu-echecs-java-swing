package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.viewcontroller.misc.RoundedBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.Format;

public class JCustomFormattedTextField extends JFormattedTextField {

    private int radius = 0;

    public JCustomFormattedTextField(Format format) {
        super(format);
        setOpaque(false);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
        if(border instanceof RoundedBorder rb) {
            setRadius(rb.getRadius());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape shape = radius > 0
                ? new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius)
                : new Rectangle(0, 0, getWidth(), getHeight());

        g2.setClip(shape);
        g2.setColor(getBackground());
        g2.fill(shape);

        super.paintComponent(g2);
        g2.dispose();
    }
}

