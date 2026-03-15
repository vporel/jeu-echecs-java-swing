package com.vivianhonghoa.chess.viewcontroller.components.startup;


import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;

import javax.swing.*;

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

