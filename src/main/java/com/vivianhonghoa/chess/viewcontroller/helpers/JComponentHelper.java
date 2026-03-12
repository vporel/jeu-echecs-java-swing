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

    public static void changeForegroundOnMouseHover(JComponent jComponent, Color normalColor, Color hoverColor) {
        jComponent.setForeground(normalColor);
        jComponent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComponent.setForeground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComponent.setForeground(normalColor);
            }
        });
    }

    public static void changeBackgroundOnMouseHover(JComponent jComponent, Color normalColor, Color hoverColor) {
        jComponent.setBackground(normalColor);
        jComponent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComponent.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComponent.setBackground(normalColor);
            }
        });
    }

    public static void changeCursorOnMouseHover(JComponent jComponent, Cursor normalCursor, Cursor hoverCursor) {
        jComponent.setCursor(normalCursor);
        jComponent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComponent.setCursor(hoverCursor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComponent.setCursor(normalCursor);
            }
        });
    }

}
