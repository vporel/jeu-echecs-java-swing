package com.vivianhonghoa.chess.viewcontroller.layouts;

import java.awt.*;
import javax.swing.*;

/**
 * A FlowLayout that correctly reports its preferred size when used inside
 * a BoxLayout or ScrollPane, so that wrapping works as expected.
 */
public class WrapLayout extends FlowLayout {

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    @Override
    public void layoutContainer(Container target) {
        super.layoutContainer(target);
        // After layout, notify the parent so BoxLayout can adjust its height
        Container parent = target.getParent();
        if (parent != null) {
            SwingUtilities.invokeLater(() -> {
                parent.revalidate();
                parent.repaint();
            });
        }
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            if (targetWidth == 0 && target.getParent() != null) {
                targetWidth = target.getParent().getSize().width;
            }
            if (targetWidth == 0) {
                return super.preferredLayoutSize(target);
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

            int totalHeight = vgap;
            int rowWidth = 0;
            int rowHeight = 0;

            for (int i = 0; i < target.getComponentCount(); i++) {
                Component c = target.getComponent(i);
                if (!c.isVisible()) continue;
                Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                    totalHeight += rowHeight + vgap;
                    rowWidth = 0;
                    rowHeight = 0;
                }
                rowWidth += d.width + hgap;
                rowHeight = Math.max(rowHeight, d.height);
            }
            totalHeight += rowHeight + vgap;

            return new Dimension(maxWidth, totalHeight);
        }
    }
}

