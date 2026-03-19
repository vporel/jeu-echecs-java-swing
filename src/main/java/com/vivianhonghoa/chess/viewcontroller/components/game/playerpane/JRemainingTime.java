package com.vivianhonghoa.chess.viewcontroller.components.game.playerpane;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JCustomPanel;
import com.vivianhonghoa.chess.viewcontroller.components.lib.JLabelWrapper;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;

import javax.swing.*;

public class JRemainingTime extends JCustomPanel {
    private final int playerNumber;
    private final GameEngine gameEngine;

    public JRemainingTime(int playerNumber, GameEngine gameEngine) {
        super();
        this.playerNumber = playerNumber;
        this.gameEngine = gameEngine;
        build();
    }

    private void build(){
        JLabel jRemainingTimeLabel = new JLabel("Remaining time", SwingConstants.CENTER);
        jRemainingTimeLabel.setForeground(Colors.shadeOfGray(150));
        JComponentHelper.setFontSize(jRemainingTimeLabel, 14);
        JLabelWrapper jRemainingTimeLabelWrapper = new JLabelWrapper(jRemainingTimeLabel, true);

        JLabel jRemainingTimeValue = new JLabel(getRemainingTimeStr(), SwingConstants.CENTER);
        jRemainingTimeValue.setForeground(Colors.shadeOfGray(200));
        JComponentHelper.setFontSize(jRemainingTimeValue, 22);
        JLabelWrapper jRemainingTimeValueWrapper = new JLabelWrapper(jRemainingTimeValue, true);
        jRemainingTimeValueWrapper.setPaddingVertical(5);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(10));
        this.add(jRemainingTimeLabelWrapper);
        this.add(jRemainingTimeValueWrapper);

        // Game engine events
        gameEngine.addObserver(new GameEngineObserver() {
            @Override
            public void onGameTimeUpdated(GameEngineEvent event) {
                jRemainingTimeValue.setText(getRemainingTimeStr());
            }
        });
    }

    private String getRemainingTimeStr(){
        Integer remainingTime = gameEngine.getPlayerContext(playerNumber).remainingTime().get();
        if(remainingTime == null){
            return "∞";
        }
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
