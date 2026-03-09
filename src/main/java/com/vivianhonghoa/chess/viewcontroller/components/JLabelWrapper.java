package com.vivianhonghoa.chess.viewcontroller.components;

import javax.swing.*;
import java.awt.*;

public class JLabelWrapper extends JPanel {

    private final JLabel label;
    private final boolean fullWidth;

    public JLabelWrapper(JLabel label) {
        this(label, false);
    }

    public JLabelWrapper(JLabel label, boolean fullWidth) {
        super();
        this.label = label;
        this.fullWidth = fullWidth;
        this.setLayout(new GridBagLayout());
        this.add(label);
    }

    public JLabel getLabel() {
        return label;
    }

    /**
     * Create padding by changing the width/height of the wrapper panel, while keeping the label centered.
     */
    public void setPadding(int left, int top, int right, int bottom) {
        Dimension pref = label.getPreferredSize();
        int width = fullWidth ? Integer.MAX_VALUE : pref.width + left + right;
        int height = pref.height + top + bottom;
        this.setMaximumSize(new Dimension(width, height));
    }

    public void setPaddingVertical(int value) {
        setPadding(0, value, 0, value);
    }

    public void setPaddingHorizontal(int value) {
        setPadding(value, 0, value, 0);
    }
}
