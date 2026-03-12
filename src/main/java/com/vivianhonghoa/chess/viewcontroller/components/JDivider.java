package com.vivianhonghoa.chess.viewcontroller.components;

import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;

public class JDivider extends JPanel {
    public static final Color DEFAULT_LINE_COLOR = Colors.LIGHTGRAY;
    public static final int DEFAULT_LINE_HEIGHT = 1;

    private final Color lineColor;
    private final int lineHeight;

    public JDivider() {
        this(DEFAULT_LINE_COLOR, DEFAULT_LINE_HEIGHT);
    }

    public JDivider(Color lineColor) {
        this(lineColor, DEFAULT_LINE_HEIGHT);
    }

    public JDivider(int lineHeight) {
        this(DEFAULT_LINE_COLOR, lineHeight);
    }

    public JDivider(Color lineColor, int lineHeight) {
        super();
        this.lineColor = lineColor;
        this.lineHeight = lineHeight;
        build();
    }

    private void build(){
        JPanel line = new JPanel();
        line.setBackground(lineColor);
        JComponentHelper.setFixedHeight(line, lineHeight);

        this.setBackground(Colors.APP_BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalStrut(5));
        this.add(line);
        this.add(Box.createHorizontalStrut(5));
        JComponentHelper.setFixedHeight(this, lineHeight);
    }
}
