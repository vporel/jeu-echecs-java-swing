package com.vivianhonghoa.chess.viewcontroller.components.game.playerpane;

import com.vivianhonghoa.chess.model.GameEngine;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.helpers.FontHelper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class JBackGiveUpButtons extends JCustomPanel {

    private final int playerNumber;
    private final GameEngine gameEngine;

    public JBackGiveUpButtons(int playerNumber, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        JLabel jBackLabelButton = new JLabel("\uf0e2");
        jBackLabelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!gameEngine.isRunning()) return;
                gameEngine.undo(playerNumber);
            }
        });

        JLabel jGiveUpLabelButton = new JLabel("\uf024");
        jGiveUpLabelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!gameEngine.isRunning()) return;
                int response = JOptionPane.showConfirmDialog(
                        JBackGiveUpButtons.this,
                        "Are you sure that you want to give up ?",
                        "Give up",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    gameEngine.giveUp(playerNumber);
                }
            }
        });

        for(JLabel jButton : List.of(jBackLabelButton, jGiveUpLabelButton)) {
            jButton.setFont(FontHelper.fontAwesome());
            JComponentHelper.setFontSize(jButton, 30);
            jButton.setBorder(null);
            JComponentHelper.changeForegroundOnMouseHover(
                    jButton,
                    Colors.WHITE,
                    Colors.SECONDARY
            );
            JComponentHelper.changeCursorOnMouseHover(
                    jButton,
                    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR),
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );
        }
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalGlue());
        this.add(jBackLabelButton);
        this.add(Box.createHorizontalStrut(20));
        this.add(jGiveUpLabelButton);
        this.add(Box.createHorizontalGlue());
        JComponentHelper.setFixedHeight(this, 50);
    }
}
