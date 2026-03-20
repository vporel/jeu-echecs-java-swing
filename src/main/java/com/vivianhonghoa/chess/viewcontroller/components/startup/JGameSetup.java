package com.vivianhonghoa.chess.viewcontroller.components.startup;

import com.vivianhonghoa.chess.model.engine.GameEngine;
import com.vivianhonghoa.chess.model.events.GameEngineEvent;
import com.vivianhonghoa.chess.model.events.GameEngineObserver;
import com.vivianhonghoa.chess.players.ConsolePlayer;
import com.vivianhonghoa.chess.viewcontroller.Colors;
import com.vivianhonghoa.chess.viewcontroller.components.lib.*;
import com.vivianhonghoa.chess.viewcontroller.console.JConsoleFrame;
import com.vivianhonghoa.chess.viewcontroller.helpers.JComponentHelper;
import com.vivianhonghoa.chess.viewcontroller.border.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class JGameSetup extends JSection {

    private final GameEngine gameEngine;
    private boolean isLimitedTimeSelected = true;
    private int selectedTimeLimit = 5; // Default time limit in minutes

    public JGameSetup() {
        super();
        this.gameEngine = GameEngine.getInstance();
        build();
    }

    private void build(){
        jContentPane.setBackground(Colors.PRIMARY_DARK_2);
        jContentPane.setBorder(new RoundedBorder(Colors.SECONDARY, 2, 20));
        jContentPane.setShadow(5);

        jContentPane.add(getTitlePane());
        jContentPane.add(Box.createVerticalStrut(20));
        jContentPane.add(getSectionTitlePane("WHO IS PLAYING?"));
        jContentPane.add(new JStartButtons((player1Name, player1, player2Name, player2) -> {
            if(player1 instanceof ConsolePlayer) showConsolePlayerFrame((ConsolePlayer) player1, 1);
            if(player2 instanceof ConsolePlayer) showConsolePlayerFrame((ConsolePlayer) player2, 2);
            gameEngine.start(player1Name, player1, player2Name, player2, isLimitedTimeSelected ? selectedTimeLimit * 60 : null, null);
        }));
        jContentPane.add(Box.createVerticalStrut(10));
        jContentPane.add(getSectionTitlePane("TIME FORMAT"));
        jContentPane.add(Box.createVerticalStrut(10));
        jContentPane.add(new JTimeSelection((isLimited, timeLimit) -> {
            this.isLimitedTimeSelected = isLimited;
            this.selectedTimeLimit = timeLimit;
        }));
        jContentPane.add(Box.createVerticalStrut(20));

    }

    private JPanel getTitlePane(){
        JLabel jTitleLabel = new JLabel("GAME SETUP");
        JComponentHelper.setFontSize(jTitleLabel, 23);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(Colors.SECONDARY);
        JLabelWrapper jTitleLabelWrapper = new JLabelWrapper(jTitleLabel, true);
        jTitleLabelWrapper.setBackground(Colors.PRIMARY_DARK_1);
        jTitleLabelWrapper.setRadius(20);
        jTitleLabelWrapper.setPaddingVertical(10);

        return jTitleLabelWrapper;
    }

    private JPanel getSectionTitlePane(String title){
        JLabel jTitleLabel = new JLabel(title);
        JComponentHelper.setFontSize(jTitleLabel, 18);
        JComponentHelper.setFontWeightBold(jTitleLabel);
        jTitleLabel.setForeground(Colors.SECONDARY);

        return new JLabelWrapper(jTitleLabel, true);
    }

    private void showConsolePlayerFrame(ConsolePlayer player, int playerNumber){
        JConsoleFrame consoleFrame = new JConsoleFrame(player, playerNumber);
        consoleFrame.setVisible(true);
        GameEngineObserver observer = new GameEngineObserver() {
            @Override
            public void onGameStopped(GameEngineEvent event) {
                consoleFrame.dispose();
                gameEngine.removeObserver(this);
            }
        };
        gameEngine.addObserver(observer);
    }

}
