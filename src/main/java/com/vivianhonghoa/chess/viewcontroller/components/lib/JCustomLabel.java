package com.vivianhonghoa.chess.viewcontroller.components.lib;

import javax.swing.*;
import java.awt.*;

public class JCustomLabel extends JLabel {

    private int glowRadius = 0;
    private Color shadowColor = new Color(0, 0, 0, 100);
    private boolean hasShadow = false;

    public JCustomLabel() { super(); }
    public JCustomLabel(String text) { super(text); }
    public JCustomLabel(String text, int horizontalAlignment) { super(text, horizontalAlignment); }

    public void setShadow(int radius, Color color) {
        this.glowRadius = radius;
        this.shadowColor = color;
        this.hasShadow = true;
        setOpaque(false);
        repaint();
    }

    public void setShadow(int radius) {
        setShadow(radius, new Color(0, 0, 0, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!hasShadow) {
            super.paintComponent(g);
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String text = getText();
        FontMetrics fm = g2.getFontMetrics(getFont());
        g2.setFont(getFont());

        int textX = computeTextX(fm);
        int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        // Draw glow layers from outer (transparent) to inner (opaque)
        for (int r = glowRadius; r >= 1; r--) {
            float ratio = 1f - (float)(r - 1) / glowRadius;
            int alpha = Math.min(255, (int)(shadowColor.getAlpha() * ratio));
            g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), alpha));
            for (int dx = -r; dx <= r; dx++) {
                for (int dy = -r; dy <= r; dy++) {
                    if (dx * dx + dy * dy <= r * r) {
                        g2.drawString(text, textX + dx, textY + dy);
                    }
                }
            }
        }

        // Draw real text on top
        g2.setColor(getForeground());
        g2.drawString(text, textX, textY);

        g2.dispose();
    }

    private int computeTextX(FontMetrics fm) {
        int textWidth = fm.stringWidth(getText());
        return switch (getHorizontalAlignment()) {
            case CENTER -> (getWidth() - textWidth) / 2;
            case RIGHT, TRAILING -> getWidth() - textWidth - getInsets().right;
            default -> getInsets().left;
        };
    }
}
