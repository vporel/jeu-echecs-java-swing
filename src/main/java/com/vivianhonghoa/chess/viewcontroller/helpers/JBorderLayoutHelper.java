package com.vivianhonghoa.chess.viewcontroller.helpers;

import javax.swing.*;
import java.awt.*;

public class JBorderLayoutHelper {

    public static void replaceComponent(JComponent jParent, String region, JComponent jNewComponent) {
        if(!(jParent.getLayout() instanceof BorderLayout borderLayout)){
            throw new IllegalArgumentException("Parent component must have a BorderLayout");
        }
        jParent.remove(borderLayout.getLayoutComponent(region));
        jParent.add(jNewComponent, region);
        jParent.revalidate();
        jParent.repaint();
    }

}
