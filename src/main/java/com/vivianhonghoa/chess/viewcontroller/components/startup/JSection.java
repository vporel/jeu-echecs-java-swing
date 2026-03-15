package com.vivianhonghoa.chess.viewcontroller.components.startup;


import com.vivianhonghoa.chess.viewcontroller.components.JCustomPanel;

import javax.swing.*;
import java.awt.*;

public class JSection extends JCustomPanel {

    protected final JCustomPanel jContentPane;

    public JSection() {
        super();
        this.jContentPane = new JCustomPanel();
        build();
    }

    private void build(){
        jContentPane.setLayout(new BoxLayout(jContentPane, BoxLayout.Y_AXIS));

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalStrut(100));
        this.add(jContentPane);
        this.add(Box.createHorizontalStrut(100));
    }
}
