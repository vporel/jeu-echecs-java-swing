package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.utils.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class JCustomButtonWithIcon extends JCustomPanel {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 40;

    private final JComponent jIcon;
    private final Orientation orientation;
    private final JLabel jTextLabel;
    private int spacing = 5;

    public JCustomButtonWithIcon(String text, JComponent jIcon) {
        this(text, jIcon, Orientation.HORIZONTAL);
    }

    public JCustomButtonWithIcon(String text, JComponent jIcon, Orientation orientation) {
        super();
        this.jIcon = jIcon;
        this.orientation = orientation;
        this.jTextLabel = new JLabel(text);
        build();
    }

    private void build() {
        if (orientation == Orientation.HORIZONTAL) {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(Box.createHorizontalGlue());
            this.add(jIcon);
            this.add(Box.createHorizontalStrut(spacing));
            this.add(jTextLabel);
            this.add(Box.createHorizontalGlue());
        } else {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(Box.createVerticalGlue());
            this.add(jIcon);
            this.add(Box.createVerticalStrut(spacing));
            this.add(jTextLabel);
            this.add(Box.createVerticalGlue());

            jIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            jTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        this.setBackground(Color.WHITE);
        JComponentHelper.setFixedSize(this, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void addActionListener(ActionListener actionListener) {
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actionListener.actionPerformed(null);
            }
        });
    }

    public void setWidth(int width) {
        JComponentHelper.setFixedWidth(this, width);
    }

    public void setHeight(int height) {
        JComponentHelper.setFixedHeight(this, height);
    }

    public JComponent getIcon() {
        return jIcon;
    }

    public String getText() {
        return jTextLabel.getText();
    }

    public JLabel getTextLabel() {
        return jTextLabel;
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        if(jTextLabel != null) {
            jTextLabel.setForeground(fg);
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if(jTextLabel != null) {
            jTextLabel.setFont(font);
        }
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
        this.removeAll();
        build();
        this.revalidate();
        this.repaint();
    }
}
