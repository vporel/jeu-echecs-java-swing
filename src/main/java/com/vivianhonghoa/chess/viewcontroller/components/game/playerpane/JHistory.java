package com.vivianhonghoa.chess.viewcontroller.components.game.playerpane;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.model.History;
import com.vivianhonghoa.chess.model.events.*;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JDivider;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JLabelWrapper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;

public class JHistory extends JCustomPanel {
    private final int playerNumber;
    private final GameEngine gameEngine;

    public JHistory(int playerNumber, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        JLabel jHistoryLabel = new JLabel("History");
        jHistoryLabel.setForeground(Colors.SECONDARY);
        JComponentHelper.setFontWeightBold(jHistoryLabel);
        JLabelWrapper jHistoryLabelWrapper = new JLabelWrapper(jHistoryLabel, true);
        jHistoryLabelWrapper.setPaddingVertical(10);

        JCustomPanel jHistoryContentPanel = new JCustomPanel();
        JComponentHelper.setMinimumHeight(jHistoryContentPanel, 100);
        jHistoryContentPanel.setLayout(new BoxLayout(jHistoryContentPanel, BoxLayout.Y_AXIS));

        JScrollPane jHistoryContentScrollPane = new JScrollPane(jHistoryContentPanel);
        jHistoryContentScrollPane.setBorder(null);
        jHistoryContentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jHistoryContentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jHistoryContentScrollPane.getViewport().setBackground(Colors.PRIMARY_DARK_2);
        jHistoryContentScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        jHistoryContentScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Colors.SECONDARY;
                this.trackColor = Colors.PRIMARY_DARK_1;
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton btn = super.createDecreaseButton(orientation);
                btn.setBackground(Colors.PRIMARY_DARK_2);
                return btn;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton btn = super.createIncreaseButton(orientation);
                btn.setBackground(Colors.PRIMARY_DARK_2);
                return btn;
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jHistoryLabelWrapper);
        this.add(new JDivider(Colors.shadeOfGray(70)));
        this.add(Box.createVerticalStrut(5));
        this.add(jHistoryContentScrollPane);

        //History events
        History history = gameEngine.getPlayerHistory(playerNumber);
        history.addObserver(new HistoryObserver() {
            @Override
            public void onChange(HistoryEvent event) {
                jHistoryContentPanel.removeAll();
                List<String> moves = history.getFormattedList();
                for(int i = 0; i < moves.size(); i++){
                    String move = (i + 1) + ". " + moves.get(i);
                    JLabel jMoveLabel = new JLabel(move);
                    jMoveLabel.setForeground(Colors.SECONDARY);

                    JLabelWrapper jMoveLabelWrapper = new JLabelWrapper(jMoveLabel, true);
                    jHistoryContentPanel.add(jMoveLabelWrapper);
                }
            }
        });
    }
}
