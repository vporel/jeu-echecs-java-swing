package com.vivianhonghoa.chess.viewcontroller.helpers;

import javax.swing.*;
import java.awt.*;

public class JComponentHelper {

    public static void setPreferredSize(JComponent jComponent, int width, int height) {
        jComponent.setPreferredSize(new Dimension(width, height));
    }

    public static void setPreferredWidth(JComponent jComponent, int width) {
        jComponent.setPreferredSize(new Dimension(width, jComponent.getPreferredSize().height));
    }

    public static void setPreferredHeight(JComponent jComponent, int height) {
        jComponent.setPreferredSize(new Dimension(jComponent.getPreferredSize().width, height));
    }

    public static void setMinimumWidth(JComponent jComponent, int width) {
        jComponent.setMinimumSize(new Dimension(width, jComponent.getMinimumSize().height));
    }

    public static void setMinimumHeight(JComponent jComponent, int height) {
        jComponent.setMinimumSize(new Dimension(jComponent.getMinimumSize().width, height));
    }

    public static void setMaximumWidth(JComponent jComponent, int width) {
        jComponent.setMaximumSize(new Dimension(width, jComponent.getMaximumSize().height));
    }

    public static void setMaximumHeight(JComponent jComponent, int height) {
        jComponent.setMaximumSize(new Dimension(jComponent.getMaximumSize().width, height));
    }

    public static void setFixedHeight(JComponent jComponent, int height) {
        jComponent.setPreferredSize(new Dimension(jComponent.getPreferredSize().width, height));
        jComponent.setMaximumSize(new Dimension(jComponent.getMaximumSize().width, height));
        jComponent.setMinimumSize(new Dimension(jComponent.getMinimumSize().width, height));
    }
    
    public static void setFixedSize(JComponent jComponent, int width, int height) {
        jComponent.setPreferredSize(new Dimension(width, height));
        jComponent.setMaximumSize(new Dimension(width, height));
        jComponent.setMinimumSize(new Dimension(width, height));
    }


    public static void setFontSize(JComponent jComponent, float size) {
        jComponent.setFont(jComponent.getFont().deriveFont(size));
    }

    public static void setItalic(JLabel jComponent) {
        jComponent.setFont(jComponent.getFont().deriveFont(jComponent.getFont().getStyle() | Font.ITALIC));
    }

    public static void setBold(JLabel jComponent) {
        jComponent.setFont(jComponent.getFont().deriveFont(jComponent.getFont().getStyle() | Font.BOLD));
    }

}
