package com.vivianhonghoa.chess.viewcontroller.components.lib;

import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.utils.Orientation;

import javax.swing.*;
import java.awt.*;

public class JDivider extends JPanel {
    public static final Color DEFAULT_LINE_COLOR = Colors.LIGHTGRAY;
    public static final int DEFAULT_LINE_HEIGHT = 1;

    private final Color lineColor;
    private final int lineSize;
    private final Orientation orientation;
    private int padding = 5;

    public JDivider() {
        this(DEFAULT_LINE_COLOR, DEFAULT_LINE_HEIGHT, Orientation.HORIZONTAL);
    }

    public JDivider(Color lineColor) {
        this(lineColor, DEFAULT_LINE_HEIGHT, Orientation.HORIZONTAL);
    }

    public JDivider(int lineSize) {
        this(DEFAULT_LINE_COLOR, lineSize, Orientation.HORIZONTAL);
    }

    public JDivider(Orientation orientation) {
        this(DEFAULT_LINE_COLOR, DEFAULT_LINE_HEIGHT, orientation);
    }

    public JDivider(Color lineColor, int lineSize) {
        this(lineColor, lineSize, Orientation.HORIZONTAL);
    }

    public JDivider(Color lineColor, int lineSize, Orientation orientation) {
        super();
        this.lineColor = lineColor;
        this.lineSize = lineSize;
        this.orientation = orientation;
        build();
    }

    private void build(){
        JPanel line = new JPanel();
        line.setBackground(lineColor);
        this.setOpaque(false);

        if (orientation == Orientation.VERTICAL) {
            JComponentHelper.setFixedWidth(line, lineSize);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(Box.createVerticalStrut(padding));
            this.add(line);
            this.add(Box.createVerticalStrut(padding));
            JComponentHelper.setFixedWidth(this, lineSize);
        } else {
            JComponentHelper.setFixedHeight(line, lineSize);
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.add(Box.createHorizontalStrut(padding));
            this.add(line);
            this.add(Box.createHorizontalStrut(padding));
            JComponentHelper.setFixedHeight(this, lineSize);
        }
    }

    public JDivider setPadding(int padding) {
        this.padding = padding;
        this.removeAll();
        build();
        this.revalidate();
        this.repaint();
        return this;
    }
}
