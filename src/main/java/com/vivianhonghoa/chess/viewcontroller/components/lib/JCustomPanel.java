package com.vivianhonghoa.chess.viewcontroller.components.lib;

import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JCustomPanel extends JPanel {
    private final static int DEFAULT_GRADIENT_ANGLE = 0;

    private int radiusTopLeft = 0;
    private int radiusTopRight = 0;
    private int radiusBottomRight = 0;
    private int radiusBottomLeft = 0;
    private int shadowSize = 0;
    private Color shadowColor = new Color(0, 0, 0, 80);
    private Border innerBorder = null;
    private List<Color> gradientColors = null;
    private int gradientAngle = DEFAULT_GRADIENT_ANGLE;
    private BufferedImage backgroundImage = null;

    public JCustomPanel() {
        this(new BorderLayout());
    }

    public JCustomPanel(LayoutManager layout) {
        super(layout);
        this.setOpaque(false);
    }

    public int getRadius() { return radiusTopLeft; }

    public void setRadius(int radius) {
        setRadius(radius, radius, radius, radius);
    }

    public void setRadius(int topLeft, int topRight, int bottomRight, int bottomLeft) {
        this.radiusTopLeft = topLeft;
        this.radiusTopRight = topRight;
        this.radiusBottomRight = bottomRight;
        this.radiusBottomLeft = bottomLeft;
        setOpaque(false);
        repaint();
    }

    public void setRadiusTopLeft(int r)     { this.radiusTopLeft = r;     setOpaque(false); repaint(); }
    public void setRadiusTopRight(int r)    { this.radiusTopRight = r;    setOpaque(false); repaint(); }
    public void setRadiusBottomRight(int r) { this.radiusBottomRight = r; setOpaque(false); repaint(); }
    public void setRadiusBottomLeft(int r)  { this.radiusBottomLeft = r;  setOpaque(false); repaint(); }

    private boolean hasRadius() {
        return radiusTopLeft > 0 || radiusTopRight > 0 || radiusBottomRight > 0 || radiusBottomLeft > 0;
    }

    private boolean isUniformRadius() {
        return radiusTopLeft == radiusTopRight && radiusTopRight == radiusBottomRight && radiusBottomRight == radiusBottomLeft;
    }

    private Shape buildShape(float x, float y, float w, float h) {
        if (!hasRadius()) return new Rectangle((int)x, (int)y, (int)w, (int)h);
        if (isUniformRadius()) return new RoundRectangle2D.Float(x, y, w, h, radiusTopLeft, radiusTopLeft);
        Path2D path = new Path2D.Float();
        path.moveTo(x + radiusTopLeft, y);
        path.lineTo(x + w - radiusTopRight, y);
        path.quadTo(x + w, y, x + w, y + radiusTopRight);
        path.lineTo(x + w, y + h - radiusBottomRight);
        path.quadTo(x + w, y + h, x + w - radiusBottomRight, y + h);
        path.lineTo(x + radiusBottomLeft, y + h);
        path.quadTo(x, y + h, x, y + h - radiusBottomLeft);
        path.lineTo(x, y + radiusTopLeft);
        path.quadTo(x, y, x + radiusTopLeft, y);
        path.closePath();
        return path;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (!isOpaque() && !hasRadius()) {
            setOpaque(true);
        }
        gradientColors = null;
        backgroundImage = null;
        repaint();
    }

    @Override
    public void setBorder(Border border) {
        this.innerBorder = border;
        if (border instanceof RoundedBorder rb) {
            setRadius(rb.getRadius());
        }
        applyBorder();
    }

    public void setShadow(int size) {
        this.shadowSize = size;
        applyBorder();
        repaint();
    }

    public void setShadow(int size, Color color) {
        this.shadowColor = color;
        setShadow(size);
    }

    /**
     * Combines innerBorder with an EmptyBorder that reserves space for the shadow,
     * so the shadow is visible outside the visual border without overwriting it.
     */
    private void applyBorder() {
        if (shadowSize > 0) {
            Border outer = BorderFactory.createEmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize);
            super.setBorder(innerBorder != null ? new CompoundBorder(outer, innerBorder) : outer);
        } else {
            super.setBorder(innerBorder);
        }
    }

    public void setLinearGradientBackground(List<Color> colors) {
        setLinearGradientBackground(colors, DEFAULT_GRADIENT_ANGLE);
    }

    public void setLinearGradientBackground(List<Color> colors, int angle) {
        this.gradientColors = colors;
        this.gradientAngle = angle;
        backgroundImage = null; // Clear background image if gradient is set, to avoid confusion
        setOpaque(false);
        repaint();
    }

    public void setBackgroundImage(InputStream inputStream) {
        try {
            this.backgroundImage = ImageIO.read(inputStream);
            gradientColors = null;
            setOpaque(false);
            repaint();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load background image", e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque() && !hasRadius() && shadowSize == 0 && gradientColors == null && backgroundImage == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        int x = insets.left - (shadowSize > 0 ? (innerBorder != null ? innerBorder.getBorderInsets(this).left : 0) : 0);
        int y = insets.top  - (shadowSize > 0 ? (innerBorder != null ? innerBorder.getBorderInsets(this).top  : 0) : 0);
        int w = getWidth()  - insets.left - insets.right  + (shadowSize > 0 ? (innerBorder != null ? innerBorder.getBorderInsets(this).left + innerBorder.getBorderInsets(this).right  : 0) : 0);
        int h = getHeight() - insets.top  - insets.bottom + (shadowSize > 0 ? (innerBorder != null ? innerBorder.getBorderInsets(this).top  + innerBorder.getBorderInsets(this).bottom : 0) : 0);

        // Paint shadow layers
        if (shadowSize > 0) {
            for (int i = shadowSize; i >= 1; i--) {
                float ratio = 1f - (float)(i - 1) / shadowSize;
                int alpha = (int) (shadowColor.getAlpha() * ratio);
                g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), alpha));
                g2.fill(buildShape(x + i, y + i, w, h));
            }
        }

        // Clip to shape
        if (hasRadius()) {
            g2.setClip(buildShape(x, y, w, h));
        }

        // Paint background image, gradient or solid color
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, x, y, w, h, null);
        } else if (gradientColors != null && gradientColors.size() >= 2) {
            double rad = Math.toRadians(gradientAngle);
            float cx = x + w / 2f;
            float cy = y + h / 2f;
            float dx = (float) Math.cos(rad) * w / 2f;
            float dy = (float) Math.sin(rad) * h / 2f;
            float[] fractions = new float[gradientColors.size()];
            for (int i = 0; i < fractions.length; i++) fractions[i] = (float) i / (fractions.length - 1);
            g2.setPaint(new LinearGradientPaint(
                    new Point2D.Float(cx - dx, cy - dy),
                    new Point2D.Float(cx + dx, cy + dy),
                    fractions,
                    gradientColors.toArray(new Color[0])
            ));
            g2.fill(buildShape(x, y, w, h));
        } else {
            g2.setPaint(getBackground());
            g2.fill(buildShape(x, y, w, h));
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
    }
}
